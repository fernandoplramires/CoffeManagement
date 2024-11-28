package br.com.ramires.gourment.coffemanagement.ui.product

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import br.com.ramires.gourment.coffemanagement.databinding.DialogNewProductBinding

class NewProductDialogFragment(private val onSaveProduct: (Double, String, String, String?) -> Unit) : DialogFragment() {

    private var _binding: DialogNewProductBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    // Activity Result Launcher para selecionar imagem da galeria
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.imageViewProductPreview.setImageURI(uri)
        } else {
            Toast.makeText(requireContext(), "Falha ao carregar imagem", Toast.LENGTH_SHORT).show()
        }
    }

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

        // Botao de upload de imagem
        binding.buttonUploadImage.setOnClickListener {
            selectImageFromGallery()
        }

        // Botão de salvar produto
        binding.buttonSaveProduct.setOnClickListener {
            val value = binding.editTextValue.text.toString().toDoubleOrNull()
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()

            if (value != null && title.isNotEmpty() && description.isNotEmpty()) {
                onSaveProduct(value, title, description, selectedImageUri?.toString())
                Toast.makeText(requireContext(), "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectImageFromGallery() {
        imagePickerLauncher.launch("image/*")
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
