package com.soyeb.zerohoursmedicalservice.data_model

data class RegistrationResponseM(
    val name: String,
    val phone_no: String,
    val email: String,
    val doctor: String,
    val reg_no: String,
    val updated_at: String,
    val created_at: String,
    val id: Int,
    val message: String,
    val error: String


)