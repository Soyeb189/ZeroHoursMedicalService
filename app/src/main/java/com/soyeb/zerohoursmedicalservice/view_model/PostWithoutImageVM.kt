package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.PostRequestWithoutImageM
import com.soyeb.zerohoursmedicalservice.data_model.PostResponseM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PostWithoutImageVM : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    var message_info = MutableLiveData<PostResponseM>()


    fun doPost(model: PostRequestWithoutImageM, activity: Activity) {

        disposable.add(
            apiService.doLrDocUploadWithoutImage(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PostResponseM>() {
                    override fun onSuccess(model: PostResponseM) {
                        //pDialog?.hide()

                        Log.e("Upload", "Success")

                        Log.e("model-->", model.toString())

                        model.let {
                            message_info.value = model
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