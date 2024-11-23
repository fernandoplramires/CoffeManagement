package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Order

class OrderRepository : OrderRepositoryInterface {

    override fun getOrders(): List<Order> {
        // Implementação real do repositório
        return emptyList()
    }

    override fun updateOrder(order: Order) {
        // Implementação real do repositório
    }
}
