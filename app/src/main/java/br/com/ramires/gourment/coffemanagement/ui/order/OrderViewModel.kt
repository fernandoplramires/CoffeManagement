package br.com.ramires.gourment.coffemanagement.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.ramires.gourment.coffemanagement.data.model.Order
import br.com.ramires.gourment.coffemanagement.data.repository.OrderRepositoryInterface

class OrderViewModel(private val repository: OrderRepositoryInterface) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    init {
        loadOrders()
    }

    fun loadOrders() {
        val data = repository.getOrders()
        _orders.value = data
    }

    fun updateOrder(updatedOrder: Order) {
        repository.updateOrder(updatedOrder)
        loadOrders()
    }

    fun expandOrder(orderId: Int) {
        _orders.value = _orders.value?.map { it.copy() }
    }
}