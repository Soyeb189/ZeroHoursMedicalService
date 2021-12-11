package com.soyeb.zerohoursmedicalservice.data_model


import com.google.gson.annotations.SerializedName

data class PostResponseM(
    @SerializedName("title")
    val title: String, // 2021-12-10T14:25:58.000000Z
    @SerializedName("user_id")
    val user_id: String, // hhhhh
    @SerializedName("description")
    val description: String, // false
    @SerializedName("updated_at")
    val updated_at: String, // 5
    @SerializedName("created_at")
    val created_at: String, // success
    @SerializedName("id")
    val id: Int, // hhhh
    @SerializedName("error")
    val error: String, // 2021-12-10T14:25:58.000000Z
    @SerializedName("message")
    val message: String // 1
)