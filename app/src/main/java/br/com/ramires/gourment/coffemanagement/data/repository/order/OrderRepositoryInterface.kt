package br.com.ramires.gourment.coffemanagement.data.repository.order

import br.com.ramires.gourment.coffemanagement.data.model.Order

interface OrderRepositoryInterface {
    suspend fun getAllOrders(): List<Order>
    suspend fun getNextProductId(): Int
    suspend fun addOrder(order: Order)
    suspend fun updateOrder(order: Order)
    suspend fun deleteOrder(orderId: Int)
}
