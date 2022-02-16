package com.soyeb.zerohoursmedicalservice.request_model

import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MessageRequestWithoutImageM {
    var pDialog: SweetAlertDialog? = null
    var sender_id: String? = null//1 for insert 2 for update
    var receiver_id: String? = null
    var message: String? = null
    //var body: MultipartBody.Part? = null
}