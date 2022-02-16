package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.MessageDataM
import com.soyeb.zerohoursmedicalservice.request_model.MessageRequestWithoutImageM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MessageWithoutImageVM : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    var message_info = MutableLiveData<MessageDataM>()


    fun doMessage(model: MessageRequestWithoutImageM, activity: Activity) {

        var pDialog: SweetAlertDialog? =  Custom_alert.showProgressDialog(activity)

        disposable.add(
            apiService.doLrDocUploadWithoutImage(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MessageDataM>() {
                    override fun onSuccess(model: MessageDataM) {

                        Log.e("Upload", "Success")

                        Log.e("model-->", model.toString())

                        model.let {
                            message_info.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        model.pDialog?.dismiss()
                        Log.e("Upload", "Failed")
                        Log.e("onError--->", "onError--" + e.toString())

                        e.printStackTrace()


                    }

                })
        )
    }
}