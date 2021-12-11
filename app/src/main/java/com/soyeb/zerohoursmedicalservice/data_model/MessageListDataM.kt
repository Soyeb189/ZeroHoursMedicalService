package com.soyeb.zerohoursmedicalservice.data_model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class MessageListDataM (

    @SerializedName("created_at")
    var created_at : String?,

    @SerializedName("error")
    var error : Boolean?,

    @SerializedName("id")
    var id : Int?,

    @SerializedName("image")
    var image : String?,

    @SerializedName("message")
    var message : String?,

    @SerializedName("receiver_id")
    var receiver_id : String?,

    @SerializedName("receiver_name")
    var receiver_name : String?,

    @SerializedName("sender_id")
    var sender_id : String?,


    @SerializedName("sender_name")
    var sender_name : String?





    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created_at)
        parcel.writeValue(error)
        parcel.writeValue(id)
        parcel.writeString(image)
        parcel.writeString(message)
        parcel.writeString(receiver_id)
        parcel.writeString(receiver_name)
        parcel.writeString(sender_id)
        parcel.writeString(sender_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageListDataM> {
        override fun createFromParcel(parcel: Parcel): MessageListDataM {
            return MessageListDataM(parcel)
        }

        override fun newArray(size: Int): Array<MessageListDataM?> {
            return arrayOfNulls(size)
        }
    }
}

