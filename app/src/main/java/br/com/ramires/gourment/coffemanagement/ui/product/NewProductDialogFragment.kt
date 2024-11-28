package br.com.ramires.gourment.coffemanagement.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import br.com.ramires.gourment.coffemanagement.databinding.DialogNewProductBinding

class NewProductDialogFragment(private val onSave: (String, String, String, String) -> Unit) : DialogFragment() {

    private var _binding: DialogNewProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNewProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa o estado do botão de salvar
        binding.buttonSaveProduct.isEnabled = false

        // Observa as mudanças nos campos de texto
        binding.editTextValue.addTextChangedListener { validateForm() }
        binding.editTextTitle.addTextChangedListener { validateForm() }
        binding.editTextDescription.addTextChangedListener { validateForm() }

        // Botão de cancelar
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        // Botão de salvar produto
        binding.buttonSaveProduct.setOnClickListener {
            val value = binding.editTextValue.text.toString()
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()
            val imageUrl = "" // Caminho padrão ou obtido via upload

            // Passa os dados para o callback e fecha o dialog
            onSave(value, title, description, imageUrl)
            Toast.makeText(requireContext(), "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun validateForm() {
        val isFormValid = binding.editTextValue.text.isNotEmpty() &&
                binding.editTextTitle.text.isNotEmpty() &&
                binding.editTextDescription.text.isNotEmpty()

        binding.buttonSaveProduct.isEnabled = isFormValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
