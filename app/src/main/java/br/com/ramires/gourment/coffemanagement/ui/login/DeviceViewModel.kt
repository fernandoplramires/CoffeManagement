package br.com.ramires.gourment.coffemanagement.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.ramires.gourment.coffemanagement.data.repository.device.DeviceRepositoryInterface
import kotlinx.coroutines.Dispatchers

class DeviceViewModel(
    private val repository: DeviceRepositoryInterface
) : ViewModel() {

    fun validateDevice(imei: String) = liveData(Dispatchers.IO) {
        try {
            val isValid = repository.isDeviceRegistered(imei)
            emit(Result.success(isValid))
        } catch (e: Exception) {
            emit(Result.failure<Boolean>(e))
        }
    }
}
