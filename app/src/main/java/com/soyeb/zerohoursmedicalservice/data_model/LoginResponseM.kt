package com.soyeb.zerohoursmedicalservice.data_model


import com.google.gson.annotations.SerializedName

data class LoginResponseM(

    @SerializedName("id")
    val id: Int, // 25
    @SerializedName("name")
    val name: String, // Soyeb
    @SerializedName("email")
    val email: String, // muktadir189@gmail.com
    @SerializedName("phone_no")
    val phoneNo: String, // 01400525639
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any = "", // null
    @SerializedName("image")
    val image: Any = "", // null
    @SerializedName("admin")
    val admin: String, // 0
    @SerializedName("doctor")
    val doctor: String, // 1
    @SerializedName("approve")
    val approve: String, // 0
    @SerializedName("reg_no")
    val regNo: String, // 123
    @SerializedName("created_at")
    val createdAt: String, // 2021-12-12T20:23:03.000000Z
    @SerializedName("updated_at")
    val updatedAt: String, // 2021-12-12T20:23:03.000000Z,
    @SerializedName("message")
    val message: String, // Success
    @SerializedName("error")
    val error: String // false







)