package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Product

class ProductRepository : ProductRepositoryInterface {
    // Implemente aqui a lógica para dados reais (se necessário).
    private val products = mutableListOf<Product>()

    override fun getProducts(): List<Product> {
        return products
    }

    override fun addProduct(product: Product) {
        products.add(product)
    }

    override fun removeProduct(product: Product) {
        products.removeIf { it.id == product.id }
    }

    override fun updateProduct(updatedProduct: Product) {
        val index = products.indexOfFirst { it.id == updatedProduct.id }
        if (index != -1) {
            products[index] = updatedProduct
        }
    }
}
