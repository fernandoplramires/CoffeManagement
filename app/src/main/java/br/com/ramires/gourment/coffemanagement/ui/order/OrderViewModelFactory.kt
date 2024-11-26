package br.com.ramires.gourment.coffemanagement.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.ramires.gourment.coffemanagement.data.repository.order.OrderRepositoryInterface

class OrderViewModelFactory(
    private val repository: OrderRepositoryInterface
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
