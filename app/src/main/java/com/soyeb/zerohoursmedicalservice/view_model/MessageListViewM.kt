package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.MessageListDataM
import com.soyeb.zerohoursmedicalservice.request_model.MessageListRequestM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MessageListViewM : ViewModel() {
    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    var messageList = MutableLiveData<List<MessageListDataM>>()

    fun getMessageList(reqmodel: MessageListRequestM, activity: Activity) {

        disposable.add(
            apiService.getMessage(reqmodel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<List<MessageListDataM>>() {
                    override fun onSuccess(model: List<MessageListDataM>) {

                        Log.e("model-->", model.toString())

                        model.let {
                            messageList.value = model
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("onError--->", "onError--" + e.toString())
                        //NetworkError.(activity, e)
                        e.printStackTrace()
                    }

                })
        )


    }
}