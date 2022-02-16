package com.soyeb.zerohoursmedicalservice.data_model


import com.google.gson.annotations.SerializedName

class UserListResponseModel : ArrayList<UserListResponseModel.UserListResponseModelItem>(){
    data class UserListResponseModelItem(
        @SerializedName("id")
        val id: String, // 3
        @SerializedName("image")
        val image: String, // http://dump.shaikot.com/default-image/default.jpg
        @SerializedName("name")
        val name: String // Mahamuda
    )
}