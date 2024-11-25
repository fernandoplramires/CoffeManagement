package br.com.ramires.gourment.coffemanagement.data.repository.device

import br.com.ramires.gourment.coffemanagement.data.model.Device

interface DeviceRepositoryInterface {
    suspend fun isDeviceRegistered(imei: String): Boolean
    suspend fun getAllDevices(): List<Device>
    suspend fun addDevice(device: Device)
    suspend fun updateDevice(device: Device)
    suspend fun deleteDevice(deviceId: Int)
}
