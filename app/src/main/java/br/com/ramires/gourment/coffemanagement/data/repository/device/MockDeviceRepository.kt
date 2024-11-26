package br.com.ramires.gourment.coffemanagement.data.repository.device

import br.com.ramires.gourment.coffemanagement.data.model.Device

class MockDeviceRepository : DeviceRepositoryInterface {

    private val devices = mutableListOf<Device>(
        Device(
            1,
            "000000000000000",
            "joselito",
            "1234",
            true
        ),
        Device(
            2,
            "000000000000001",
            "hermes",
            "1234",
            false
        )
    )

    override suspend fun isDeviceRegistered(deviceId: String): Boolean {
        val device = devices.find { it.deviceId == deviceId && it.status == true }
        return device?.status ?: false
    }

    override suspend fun isUserValid(username: String, password: String): Boolean {
        val device = devices.find { it.username == username && it.password == password && it.status == true }
        return device?.status ?: false
    }

    override suspend fun getAllDevices(): List<Device> {
        return devices
    }

    override suspend fun addDevice(device: Device) {
        devices.add(device)
    }

    override suspend fun updateDevice(device: Device) {
        val index = devices.indexOfFirst { it.id == device.id }
        if (index != -1) {
            devices[index] = device
        } else {
            throw IllegalArgumentException("Device com id ${device.id} não encontrado.")
        }
    }

    override suspend fun deleteDevice(deviceId: Int) {
        val index = devices.indexOfFirst { it.id == deviceId }
        if (index != -1) {
            devices.removeAt(index)
        } else {
            throw IllegalArgumentException("Device com id ${deviceId} não encontrado.")
        }
    }
}
