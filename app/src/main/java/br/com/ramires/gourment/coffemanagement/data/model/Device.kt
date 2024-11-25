package br.com.ramires.gourment.coffemanagement.data.model

import com.google.firebase.firestore.PropertyName

data class Device(
    @PropertyName("id") var id: Int? = null,
    @PropertyName("imei") val imei: String? = null,
    @PropertyName("status") val status: Boolean? = null
){
    // Construtor vazio para o Firestore
    constructor() : this(null, null, null)
}