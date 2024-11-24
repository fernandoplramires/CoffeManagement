package br.com.ramires.gourment.coffemanagement.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ramires.gourment.coffemanagement.data.model.Product
import br.com.ramires.gourment.coffemanagement.data.repository.product.ProductRepositoryInterface
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepositoryInterface) : ViewModel() {

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
        viewModelScope.launch {
            try {
                val orderList = repository.getAllProducts()
                _products.postValue(orderList)
            } catch (e: Exception) {
                // Trate exceções, se necessário
                e.printStackTrace()
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
                loadProducts()
            } catch (e: Exception) {
                // Trate exceções, se necessário
                e.printStackTrace()
            }
        }
    }

    fun removeProduct(productId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(productId)
                _products.value = repository.getAllProducts().toList() // Garante nova referência
            } catch (e: Exception) {
                // Trate exceções, se necessário
                e.printStackTrace()
            }
        }
    }

    fun startEditingProduct(product: Product) {
        _editingProduct.value = product
    }

    fun saveEditedProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.updateProduct(product)
                _products.value = repository.getAllProducts() // Atualiza o LiveData após salvar
            } catch (e: Exception) {
                // Trate exceções, se necessário
                e.printStackTrace()
            }
        }
    }
}
