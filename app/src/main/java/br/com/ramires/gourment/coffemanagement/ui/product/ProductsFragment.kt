package br.com.ramires.gourment.coffemanagement.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ramires.gourment.coffemanagement.data.model.Product
import br.com.ramires.gourment.coffemanagement.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        // Botão de adicionar novo produto
        binding.buttonAddProduct.setOnClickListener {
            val dialog = NewProductDialogFragment { value, title, description, imageUrl ->
                val newProduct = Product(
                    id = System.currentTimeMillis().toString(),
                    name = title,
                    description = description,
                    price = value.toDoubleOrNull() ?: 0.0,
                    imageUrl = imageUrl
                )
                viewModel.addProduct(newProduct)
            }
            dialog.show(parentFragmentManager, "NewProductDialogFragment")
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(
            onEditClick = { product ->
                // Inicia a edição do produto
                viewModel.startEditingProduct(product)
            },
            onRemoveClick = { product ->
                // Remove o produto
                showRemoveConfirmationDialog(product)
                //viewModel.removeProduct(product)
            },
            onSaveClick = { updatedProduct ->
                // Salva o produto editado
                viewModel.saveEditedProduct(updatedProduct)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun showRemoveConfirmationDialog(product: Product) {
        val dialog = RemoveConfirmationDialogFragment { confirmed ->
            if (confirmed) {
                viewModel.removeProduct(product)
                Toast.makeText(requireContext(), "Produto removido com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Exclusão cancelada", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show(childFragmentManager, "RemoveConfirmationDialog")
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            if (products.isNullOrEmpty()) {
                binding.textViewEmptyState.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.textViewEmptyState.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val REQUEST_IMAGE_PICK: Int = 1001
    }
}
