package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.MessageDataM
import com.soyeb.zerohoursmedicalservice.request_model.MessageRequestM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MessageVM : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    var lr_doc_upload_info = MutableLiveData<MessageDataM>()


    fun doLoanReqDocUpload(model: MessageRequestM, activity: Activity) {

        disposable.add(
            apiService.doLrDocUpload(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MessageDataM>() {
                    override fun onSuccess(model: MessageDataM) {
                        //pDialog?.hide()

                        Log.e("Upload", "Success")

                        Log.e("model-->", model.toString())

                        model.let {
                            lr_doc_upload_info.value = model
                        }

                    }

                    override fun onError(e: Throwable) {
                        //pDialog?.hide()
                        Log.e("Upload", "Failed")
                        Log.e("onError--->", "onError--" + e.toString())

                        e.printStackTrace()


                    }

                })
        )
    }
}