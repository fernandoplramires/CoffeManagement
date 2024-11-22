package br.com.ramires.gourment.coffemanagement.ui.product

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.ramires.gourment.coffemanagement.R
import br.com.ramires.gourment.coffemanagement.data.model.Product
import br.com.ramires.gourment.coffemanagement.databinding.ItemProductBinding
import com.bumptech.glide.Glide

class ProductAdapter(
    private val onEditClick: (Product) -> Unit,
    private val onRemoveClick: (Product) -> Unit,
    private val onSaveClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList = mutableListOf<Product>()
    private var currentEditingPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        if (holder.isEditing) {
            currentEditingPosition = position
        }
    }

    override fun getItemCount(): Int = productList.size

    fun submitList(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    fun getCurrentEditingPosition(): Int? = currentEditingPosition

    fun updateImageForProduct(position: Int, imageUri: String) {
        val product = productList[position]
        val updatedProduct = product.copy(imageUrl = imageUri)
        productList[position] = updatedProduct
        notifyItemChanged(position)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        var isEditing = false
            private set

        fun bind(product: Product) {
            binding.textViewName.text = product.name
            binding.textViewDescription.text = product.description
            binding.textViewPrice.text = "R$ ${String.format("%.2f", product.price)}"
            binding.editTextName.setText(product.name)
            binding.editTextDescription.setText(product.description)
            binding.editTextPrice.setText(product.price.toString())

            // Carregar imagem
            if (product.imageUrl.isNullOrEmpty()) {
                binding.imageViewProduct.setImageResource(R.drawable.ic_placeholder)
            } else {
                Glide.with(binding.root.context)
                    .load(product.imageUrl)
                    .into(binding.imageViewProduct)
            }

            binding.buttonEdit.setOnClickListener {
                toggleEditing(true)
            }

            binding.buttonSave.setOnClickListener {
                val updatedProduct = product.copy(
                    name = binding.editTextName.text.toString(),
                    description = binding.editTextDescription.text.toString(),
                    price = binding.editTextPrice.text.toString().toDoubleOrNull() ?: product.price,
                    imageUrl = product.imageUrl // Atualiza o URI da imagem, se necessário
                )
                onSaveClick(updatedProduct)
                toggleEditing(false)

                Toast.makeText(binding.root.context, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show()
            }

            binding.buttonRemove.setOnClickListener {
                onRemoveClick(product)
            }

            binding.buttonUploadImage.setOnClickListener {
                selectImageFromGallery()
            }

            toggleEditing(false)
        }

        private fun toggleEditing(enabled: Boolean) {
            isEditing = enabled

            binding.textViewName.visibility = if (enabled) View.GONE else View.VISIBLE
            binding.editTextName.visibility = if (enabled) View.VISIBLE else View.GONE

            binding.textViewDescription.visibility = if (enabled) View.GONE else View.VISIBLE
            binding.editTextDescription.visibility = if (enabled) View.VISIBLE else View.GONE

            binding.textViewPrice.visibility = if (enabled) View.GONE else View.VISIBLE
            binding.editTextPrice.visibility = if (enabled) View.VISIBLE else View.GONE

            binding.buttonSave.isEnabled = enabled
            binding.buttonEdit.isEnabled = !enabled

            binding.buttonUploadImage.visibility = if (enabled) View.VISIBLE else View.GONE
        }

        private fun selectImageFromGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            (binding.root.context as? Activity)?.startActivityForResult(intent, ProductsFragment.REQUEST_IMAGE_PICK)
        }
    }
}

