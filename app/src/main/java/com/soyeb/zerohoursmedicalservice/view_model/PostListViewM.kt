package com.soyeb.zerohoursmedicalservice.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doctortree.doctortree.retrofit.ApiService
import com.soyeb.zerohoursmedicalservice.data_model.PostListResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PostListViewM : ViewModel() {
    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    var add_opt_res = MutableLiveData<List<PostListResponseModel.PostListResponseModelItem>>()

    fun doPostRequest(activity: Activity) {

        disposable.add(
            apiService.call
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<List<PostListResponseModel.PostListResponseModelItem>>() {
                    override fun onSuccess(model: List<PostListResponseModel.PostListResponseModelItem>) {

                        Log.e("model-->", model.toString())

                        model.let {
                            add_opt_res.value = model
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