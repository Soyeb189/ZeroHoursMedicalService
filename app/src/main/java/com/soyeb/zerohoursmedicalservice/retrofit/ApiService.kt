package com.doctortree.doctortree.retrofit

import com.google.gson.GsonBuilder
import com.soyeb.zerohoursmedicalservice.data_model.*
import com.soyeb.zerohoursmedicalservice.request_model.*
import com.soyeb.zerohoursmedicalservice.util.Constrants_Variable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    var baseurl = "http://dump.shaikot.com/api/v1.0/"

    //var baseurl = "https://raw.githubusercontent.com/"
    var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
        .connectTimeout(Constrants_Variable.retrofit_connection_timeout_in_second, TimeUnit.SECONDS)
        .readTimeout(Constrants_Variable.retrofit_read_timeout_in_second, TimeUnit.SECONDS)
        .writeTimeout(Constrants_Variable.retrofit_wright_timeout_in_second, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", Constrants_Variable.headerUserPass)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(Api::class.java)

    fun getLogin(model: LoginRequestM): Single<LoginResponseM> {
        return api.doLogin(
            model.phone_no,
            model.password
        )
    }

    fun getRegistration(model: RegistrationRequestM): Single<RegistrationResponseM> {
        return api.doRegister(
            model.email,
            model.password,
            model.name,
            model.doctor,
            model.reg_no,
            model.password_confirmation,
            model.phone_no
        )
    }

    val call = api.getPost()

    fun  doLrDocUploadWithoutImage(model: PostRequestWithoutImageM): Single<PostResponseM> {
        return api.doPost(
            model.title,
            model.description,
            model.user_id
        )
    }

    fun getMessage(model: MessageListRequestM): Single<List<MessageListDataM>> {
        return api.getMessage(
            model.receiver_id,
            model.sender_id
        )
    }

    fun getUserList(model: UserListRequestM): Single<List<UserListResponseModel.UserListResponseModelItem>> {
        return api.getUserList(
            model.user_id
        )
    }

    fun  doLrDocUpload(requestModel: MessageRequestM): Single<MessageDataM> {
        return api.doLrDocUpload(
            requestModel.sender_id,
            requestModel.receiver_id,
            requestModel.message,
            requestModel.body,
        )
    }

    fun  doLrDocUploadWithoutImage(model: MessageRequestWithoutImageM): Single<MessageDataM> {
        return api.doLrDocUploadWithoutImage(
            model.sender_id,
            model.receiver_id,
            model.message
        )
    }

/*val call = api.getMenu()

fun getDieses(model: DiesesListRequestM): Single<List<DiesesListDataM>> {
    return api.getDieses(
        model.id
    )
}

fun getSolution(model: SolutionListRequestM): Single<List<SolutionListDataM>> {
    return api.getSolution(
        model.menu_id,
        model.disease_id,
        model.type
    )
}

fun doRegister(model: RegistrationRequestM): Single<RegistrationDataM> {
    return api.doRegistration(
        model.email,
        model.password,
        model.name,
        model.password_confirmation
    )
}

fun doLogin(model: LoginRequestM): Single<LoginDataM> {
    return api.doLogin(
        model.email,
        model.password
    )
}

fun getMessage(model: MessageListRequestM): Single<List<MessageListDataM>> {
    return api.getMessage(
        model.receiver_id,
        model.sender_id
    )
}

fun  doLrDocUpload(requestModel: MessageRequestM): Single<MessageDataM> {
    return api.doLrDocUpload(
        requestModel.sender_id,
        requestModel.receiver_id,
        requestModel.message,
        requestModel.body,
    )
}

fun  doLrDocUploadWithoutImage(model: MessageRequestWithoutImageM): Single<MessageDataM> {
    return api.doLrDocUploadWithoutImage(
        model.sender_id,
        model.receiver_id,
        model.message
    )
}*/
}