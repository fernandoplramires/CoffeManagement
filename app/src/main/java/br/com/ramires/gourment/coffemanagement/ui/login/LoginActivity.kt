package br.com.ramires.gourment.coffemanagement.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.ramires.gourment.coffemanagement.MainActivity
import br.com.ramires.gourment.coffemanagement.data.repository.device.DeviceRepositoryInterface
import br.com.ramires.gourment.coffemanagement.data.repository.device.FirebaseDeviceRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: DeviceViewModel
    private lateinit var telephonyManager: TelephonyManager
    private var selectedRepositoryType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar TelephonyManager
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        // Configurar o ViewModel
        val repository: DeviceRepositoryInterface = FirebaseDeviceRepository()
        val factory = DeviceViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(DeviceViewModel::class.java)

        promptUserForRepository(this)
    }

    private fun promptUserForRepository(context: Context) {
        val options = arrayOf("Mock Repository", "Firebase Repository")
        AlertDialog.Builder(this)
            .setTitle("Escolha o Tipo de Repositório")
            .setSingleChoiceItems(options, -1) { dialog, which ->
                selectedRepositoryType = if (which == 0) "MOCK" else "REAL"
                dialog.dismiss()

                validateDevice(context)
            }
            .setCancelable(false)
            .show()
    }

    private fun validateDevice(context: Context) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        // Nao verifica IMEI para emuladores
        if (Build.FINGERPRINT.contains("emu64", ignoreCase = true)) {
            navigateToMainActivity()
        } else {
            try {
                val imei = telephonyManager.imei ?: throw IllegalStateException("IMEI não disponível")

                viewModel.validateDevice(imei).observe(this) { result ->
                    result.onSuccess { isValid ->
                        if (isValid) {
                            navigateToMainActivity()
                        } else {
                            showErrorAndClose("IMEI não cadastrado ou inválido.")
                        }
                    }.onFailure { error ->
                        showErrorAndClose("Erro ao validar dispositivo: ${error.message}")
                    }
                }
            } catch (e: Exception) {
                showErrorAndClose("Erro ao obter IMEI: ${e.message}")
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("REPOSITORY_TYPE", selectedRepositoryType)
        }
        startActivity(intent)
        finish()
    }

    private fun showErrorAndClose(message: String) {
        Log.e("validateDevice", message)

        AlertDialog.Builder(this)
            .setTitle("AVISO")
            .setMessage("IMEI nao cadastrado! Acesso ao aplicativo negado! Por gentileza entre em contato com o administrador.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish() // Fecha o aplicativo após o clique em "OK"
            }
            .setCancelable(false) // Impede que o usuário feche o diálogo sem clicar em "OK"
            .create()
            .show()
    }
}
