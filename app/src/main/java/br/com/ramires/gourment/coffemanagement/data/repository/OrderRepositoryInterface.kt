package br.com.ramires.gourment.coffemanagement.data.repository

import br.com.ramires.gourment.coffemanagement.data.model.Order

interface OrderRepositoryInterface {
    fun getOrders(): List<Order>
    fun updateOrder(order: Order)
}
