package com.soyeb.zerohoursmedicalservice.data_model

import okhttp3.MultipartBody
import okhttp3.RequestBody


class PostRequestWithoutImageM {
    var title: String? = null//1 for insert 2 for update
    var description: String? = null
    var user_id: String? = null
    //var body: MultipartBody.Part? = null
}