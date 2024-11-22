package br.com.ramires.gourment.coffemanagement.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ramires.gourment.coffemanagement.data.model.Product
import br.com.ramires.gourment.coffemanagement.data.repository.MockProductRepository
import br.com.ramires.gourment.coffemanagement.data.repository.ProductRepository
import br.com.ramires.gourment.coffemanagement.data.repository.ProductRepositoryInterface

class ProductViewModel : ViewModel() {

    private val repository: ProductRepositoryInterface =
        if (USE_MOCKS) MockProductRepository() else ProductRepository()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products
    private val _editingProduct = MutableLiveData<Product?>()
    val editingProduct: LiveData<Product?> get() = _editingProduct
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = repository.getProducts()
    }

    fun addProduct(product: Product) {
        repository.addProduct(product)
        loadProducts()
    }

    fun removeProduct(product: Product) {
        repository.removeProduct(product)
        _products.value = repository.getProducts().toList() // Garante nova referência
    }

    fun startEditingProduct(product: Product) {
        _editingProduct.value = product
    }

    fun saveEditedProduct(updatedProduct: Product) {
        repository.updateProduct(updatedProduct)
        _products.value = repository.getProducts() // Atualiza o LiveData após salvar

    }

    fun updateProduct(updatedProduct: Product) {
        // Atualiza o repositório (mock ou real)
        repository.updateProduct(updatedProduct)

        // Atualiza a lista no LiveData
        val updatedList = products.value?.map { product ->
            if (product.id == updatedProduct.id) updatedProduct else product
        }
        _products.value = updatedList
    }

    companion object {
        private const val USE_MOCKS = true // Alterne para false para usar o repositório real
    }
}
