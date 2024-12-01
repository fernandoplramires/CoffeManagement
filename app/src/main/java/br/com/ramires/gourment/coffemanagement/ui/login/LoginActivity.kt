package br.com.ramires.gourment.coffemanagement.ui.login

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import br.com.ramires.gourment.coffemanagement.MainActivity
import br.com.ramires.gourment.coffemanagement.data.repository.device.DeviceRepositoryInterface
import br.com.ramires.gourment.coffemanagement.data.repository.device.FirebaseDeviceRepository
import br.com.ramires.gourment.coffemanagement.data.repository.device.MockDeviceRepository
import br.com.ramires.gourment.coffemanagement.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var deviceRepository: DeviceRepositoryInterface? = null
    private lateinit var repositoryType: String
    private lateinit var deviceViewModel: DeviceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        promptUserForRepository()
    }

    private fun promptUserForRepository() {
        val options = arrayOf("Mock Repository", "Real Repository")
        AlertDialog.Builder(this)
            .setTitle("Escolha o Tipo de Repositório")
            .setSingleChoiceItems(options, -1) { dialog, which ->
                if (which == 0) {
                    deviceRepository = MockDeviceRepository()
                    repositoryType = "MOCK"
                } else {
                    deviceRepository = FirebaseDeviceRepository()
                    repositoryType = "REAL"
                }

                deviceViewModel = ViewModelProvider(
                    this,
                    DeviceViewModelFactory(deviceRepository!!)
                )[DeviceViewModel::class.java]

                dialog.dismiss()
                validateDevice()
            }
            .setCancelable(false)
            .show()
    }

    private fun validateDevice() {
        val deviceId = deviceViewModel.getDeviceId(this)
        lifecycleScope.launch {
            val isRegistered = deviceViewModel.isDeviceRegistered(deviceId)
            if (isRegistered) {
                // Device IO válido, prosseguir para MainActivity
                MainActivity.start(this@LoginActivity, repositoryType)
            } else {
                // Abrir login de usuário e senha
                showUserLoginDialog()
            }
        }
    }

    private fun showUserLoginDialog() {
        val loginDialog = UserLoginDialogFragment { username, password ->
            lifecycleScope.launch {
                val isValid = deviceViewModel.isUserValid(username, password)
                if (isValid) {
                    MainActivity.start(this@LoginActivity, repositoryType)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Acesso negado: Usuário/senha/permissao inválidos.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
        loginDialog.show(supportFragmentManager, "UserLoginDialog")
    }
}
