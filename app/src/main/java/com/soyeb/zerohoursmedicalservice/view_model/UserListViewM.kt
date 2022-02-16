package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.UserListRequestM
import com.soyeb.zerohoursmedicalservice.data_model.UserListResponseModel
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserListViewM : ViewModel() {
    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    var userList = MutableLiveData<List<UserListResponseModel.UserListResponseModelItem>>()

    fun getUserList(reqmodel: UserListRequestM, activity: Activity) {

        var pDialog: SweetAlertDialog? =  Custom_alert.showProgressDialog(activity)

        disposable.add(
            apiService.getUserList(reqmodel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<List<UserListResponseModel.UserListResponseModelItem>>() {
                    override fun onSuccess(model: List<UserListResponseModel.UserListResponseModelItem>) {

                        Log.e("model-->", model.toString())

                        model.let {
                            userList.value = model
                        }
                    }

                    override fun onError(e: Throwable) {
                        pDialog?.hide()
                        Log.e("onError--->", "onError--" + e.toString())
                        //NetworkError.(activity, e)
                        e.printStackTrace()
                    }

                })
        )


    }
}