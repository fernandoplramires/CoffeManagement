package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Product

class MockProductRepository : ProductRepositoryInterface {

    private val mockProducts = mutableListOf(
        Product(id = "1", name = "Cookie Cake Red Velvet Oreo 300g", description = "Acompanhada de muitoooo creme de chocolate branco com pedaços de Oreo nessa fatia enorme de cookie!!", price = 35.5, imageUrl = "https://via.placeholder.com/150"),
        Product(id = "2", name = "Cupcake Buenasso 300g", description = "Adicionamos gotas de chocolate branco na massa para dar mais um toque especial.", price = 20.0, imageUrl = "https://via.placeholder.com/150")
    )

    override fun getProducts(): List<Product> = mockProducts

    override fun addProduct(product: Product) {
        mockProducts.add(product)
    }

    override fun removeProduct(product: Product) {
        val removed = mockProducts.removeIf { it.id == product.id }
        if (!removed) {
            throw IllegalStateException("Produto não encontrado para remoção!")
        }
    }

    override fun updateProduct(updatedProduct: Product) {
        mockProducts.replaceAll { if (it.id == updatedProduct.id) updatedProduct else it }
    }
}
