package br.com.ramires.gourment.coffemanagement.data.model

enum class OrderStatus(val displayName: String) {
    NOVO("Novo"),
    CANCELADO("Cancelado"),
    PREPARACAO("Em preparação"),
    FINALIZADO("Finalizado")
}

data class OrderDetail(
    val productName: String,
    val quantity: Int
)

data class Order(
    val id: Int,
    val details: List<OrderDetail>,
    val totalPrice: Double,
    val email: String?,
    val phone: String?,
    val zipCode: String?,
    val complement: String?,
    val number: String?,
    val status: OrderStatus
)
