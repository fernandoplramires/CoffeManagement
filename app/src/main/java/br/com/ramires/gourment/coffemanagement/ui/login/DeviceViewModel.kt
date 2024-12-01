package br.com.ramires.gourment.coffemanagement.ui.login

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.ramires.gourment.coffemanagement.data.repository.device.DeviceRepositoryInterface

class DeviceViewModel(private val repository: DeviceRepositoryInterface) : ViewModel() {

    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    suspend fun isDeviceRegistered(deviceId: String): Boolean {
        val isDeviceRegistered = repository.isDeviceRegistered(deviceId)
        Log.d("DeviceViewModel", "isDeviceRegistered: repository.isDeviceRegistered($deviceId) = $isDeviceRegistered")
        return isDeviceRegistered
    }

    suspend fun isUserValid(username: String, password: String): Boolean {
        val isUserValid = repository.isUserValid(username, password)
        Log.d("DeviceViewModel", "isUserValid: repository.isUserValid($username, $username) = $isUserValid")
        return isUserValid
    }
}
