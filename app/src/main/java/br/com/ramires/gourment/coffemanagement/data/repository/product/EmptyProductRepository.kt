package br.com.ramires.gourment.coffemanagement.data.repository.product

import br.com.ramires.gourment.coffemanagement.data.model.Product

class EmptyProductRepository : ProductRepositoryInterface {

    private var currentMaxId = 0
    private val products = mutableListOf<Product>()

    override suspend fun getAllProducts(): List<Product> {
        return products
    }

    override suspend fun getNextProductId(): Int {
        return ++currentMaxId
    }

    override suspend fun addProduct(product: Product) {
        product.id = getNextProductId()
        products.add(product)
    }

    override suspend fun updateProduct(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
        } else {
            throw IllegalArgumentException("Produto não encontrado para atualização!")
        }
    }

    override suspend fun deleteProduct(productId: Int) {
        val index = products.indexOfFirst { it.id == productId }
        if (index != -1) {
            products.removeAt(index)
        } else {
            throw IllegalArgumentException("Produto não encontrado para remoção!")
        }
    }
}
