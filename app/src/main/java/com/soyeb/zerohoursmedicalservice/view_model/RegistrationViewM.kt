package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.RegistrationResponseM
import com.soyeb.zerohoursmedicalservice.request_model.LoginRequestM
import com.soyeb.zerohoursmedicalservice.request_model.RegistrationRequestM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RegistrationViewM : ViewModel() {

    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    var login = MutableLiveData<RegistrationResponseM>()

    fun doRegistration(model: RegistrationRequestM, activity: Activity) {

        disposable.add(
            apiService.getRegistration(model)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<RegistrationResponseM>() {
                    override fun onSuccess(model: RegistrationResponseM) {

                        Log.e("model-->", model.toString())

                        model.let {
                            login.value = model
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("onError--->", "onError--$e")
                        //NetworkError.(activity, e)
                        e.printStackTrace()
                    }

                })
        )


    }
}