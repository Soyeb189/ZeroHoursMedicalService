package com.soyeb.zerohoursmedicalservice.data_model


import com.google.gson.annotations.SerializedName

class PostListResponseModel : ArrayList<PostListResponseModel.PostListResponseModelItem>(){
    data class PostListResponseModelItem(
        @SerializedName("created_at")
        val createdAt: String, // 2021-12-03T14:45:12.000000Z
        @SerializedName("description")
        val description: String, // hhhhh
        @SerializedName("email")
        val email: String, // admin@admin.com
        @SerializedName("phone_no")
        val phone_no: String, // admin@admin.com
        @SerializedName("error")
        val error: Boolean, // false
        @SerializedName("id")
        val id: Int, // 3
        @SerializedName("user_id")
        val user_id: String, // 3
        @SerializedName("image")
        val image: String, // http://dump.shaikot.com/default-image/default.jpg
        @SerializedName("name")
        val name: String, // Admin
        @SerializedName("title")
        val title: String, // hhhh
        @SerializedName("user_image")
        val userImage: String // http://dump.shaikot.com/default-image/default.jpg
    )
}