package br.com.ramires.gourment.coffemanagement.data.repository.product

import br.com.ramires.gourment.coffemanagement.data.model.Product

class MockProductRepository : ProductRepositoryInterface {

    private var currentMaxId = 0
    private val products = mutableListOf<Product>(
        Product(
            1,
            "Cookie Cake Red Velvet Oreo 300g",
            "Acompanhada de muitoooo creme de chocolate branco com pedaços de Oreo nessa fatia enorme de cookie!!",
            35.5,
            "https://via.placeholder.com/150"
        ),
        Product(
            2,
            "Cupcake Buenasso 300g",
            "Adicionamos gotas de chocolate branco na massa para dar mais um toque especial.",
            20.0,
            "https://via.placeholder.com/150"
        )
    )

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
