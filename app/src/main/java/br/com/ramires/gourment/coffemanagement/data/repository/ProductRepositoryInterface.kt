package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Product

interface ProductRepositoryInterface {
    fun getProducts(): List<Product>
    fun addProduct(product: Product)
    fun removeProduct(product: Product)
    fun updateProduct(updatedProduct: Product)
}
