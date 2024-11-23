package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Order
import br.com.ramires.gourment.coffemanagement.data.model.OrderDetail
import br.com.ramires.gourment.coffemanagement.data.model.OrderStatus

class MockOrderRepository : OrderRepositoryInterface {

    private val orders = mutableListOf<Order>(
        Order(
            1,
            listOf(
                OrderDetail(
                    "Produto A",
                    1
                ),
                OrderDetail(
                    "Produto B",
                    2
                )
            ),
            150.0,
            "cliente@email.com",
            "123456789",
            "12345-678",
            "Apto 101",
            "10",
            OrderStatus.NOVO
        ),
        Order(
            2,
            listOf(
                OrderDetail(
                "Produto C",
                1
                )
            ),
            75.0,
            "outro@email.com",
            "987654321",
            "87654-321",
            "Casa",
            "20",
            OrderStatus.PREPARACAO
        )
    )

    override fun getOrders(): List<Order> {
        return orders
    }

    override fun updateOrder(order: Order) {
        val index = orders.indexOfFirst { it.id == order.id }
        if (index != -1) {
            orders[index] = order
        } else {
            throw IllegalArgumentException("Pedido com id ${order.id} não encontrado.")
        }
    }
}
