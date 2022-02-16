package com.doctortree.doctortree.retrofit

import com.soyeb.zerohoursmedicalservice.data_model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @POST("login")
    fun doLogin(
        @Field("phone_no") phone_no:String?,
        @Field("password") password:String?
    ): Single<LoginResponseM>


    @FormUrlEncoded
    @POST("register")
    fun doRegister(
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("name") name:String?,
        @Field("doctor") doctor:String?,
        @Field("reg_no") reg_no:String?,
        @Field("password_confirmation") password_confirmation:String?,
        @Field("phone_no") phone_no:String?
    ): Single<RegistrationResponseM>


    @GET("posts")
    fun getPost(): Single<List<PostListResponseModel.PostListResponseModelItem>>

//    @Multipart
//    //@FormUrlEncoded
//    @POST("message")
//    fun doLrDocUpload(
//        @Part("sender_id") sender_id: RequestBody?,
//        @Part("receiver_id") receiver_id: RequestBody?,
//        @Part("message") message: RequestBody?,
//        @Part photo : MultipartBody.Part?
//    ): Single<MessageDataM>

    // @Multipart
    @FormUrlEncoded
    @POST("save-post")
    fun doPost(
        @Field("title") title: String?,
        @Field("description") description: String?,
        @Field("user_id") user_id: String?
        // @Part image: List<MultipartBody.Part?>?
    ): Single<PostResponseM>

    @GET("view/message/receiver/{user_id}")
    fun getUserList(
        @Path("user_id") user_id:String?
    ): Single<List<UserListResponseModel.UserListResponseModelItem>>

    @GET("view/message/{sender_id}/{receiver_id}")
    fun getMessage(
        @Path("receiver_id") receiver_id:String?,
        @Path("sender_id") sender_id:String?

    ): Single<List<MessageListDataM>>

    @Multipart
    //@FormUrlEncoded
    @POST("message")
    fun doLrDocUpload(
        @Part("sender_id") sender_id: RequestBody?,
        @Part("receiver_id") receiver_id: RequestBody?,
        @Part("message") message: RequestBody?,
        @Part photo : MultipartBody.Part?
    ): Single<MessageDataM>

    // @Multipart
    @FormUrlEncoded
    @POST("message")
    fun doLrDocUploadWithoutImage(
        @Field("sender_id") sender_id: String?,
        @Field("receiver_id") receiver_id: String?,
        @Field("message") message: String?,
        // @Part image: List<MultipartBody.Part?>?
    ): Single<MessageDataM>



   /*  @GET("menu/disease/treatment/{menu_id}/{disease_id}/{type}")
    fun getSolution(
        @Path("menu_id") menu_id:String?,
        @Path("disease_id") disease_id:String?,
        @Path("type") type:String?
    ): Single<List<SolutionListDataM>>

    @FormUrlEncoded
    @POST("register")
    fun doRegistration(
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("name") name:String?,
        @Field("password_confirmation") password_confirmation:String?
    ): Single<RegistrationDataM>

    @FormUrlEncoded
    @POST("login")
    fun doLogin(
        @Field("email") email:String?,
        @Field("password") password:String?
    ): Single<LoginDataM>

    @GET("view/message/{sender_id}/{receiver_id}")
    fun getMessage(
        @Path("receiver_id") receiver_id:String?,
        @Path("sender_id") sender_id:String?

    ): Single<List<MessageListDataM>>

    @Multipart
    //@FormUrlEncoded
    @POST("message")
    fun doLrDocUpload(
        @Part("sender_id") sender_id: RequestBody?,
        @Part("receiver_id") receiver_id: RequestBody?,
        @Part("message") message: RequestBody?,
        @Part photo : MultipartBody.Part?
    ): Single<MessageDataM>

    // @Multipart
    @FormUrlEncoded
    @POST("message")
    fun doLrDocUploadWithoutImage(
        @Field("sender_id") sender_id: String?,
        @Field("receiver_id") receiver_id: String?,
        @Field("message") message: String?,
        // @Part image: List<MultipartBody.Part?>?
    ): Single<MessageDataM>*/


}