package com.soyeb.zerohoursmedicalservice.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
class User : Serializable {
    @ColumnInfo(name = "user_name")
    var name: String? = null

    @ColumnInfo(name = "user_email")
    var email: String? = null

    @ColumnInfo(name = "user_phone")
    var phoneNumber: String? = null

    @ColumnInfo(name = "user_password")
    var password: String? = null

    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "checked_val")
    var checkValue = 0

    @ColumnInfo(name = "pro_image")
    var image: String? = null

    @ColumnInfo(name = "device_uuid")
    var uuid: String? = null

    constructor(
        name: String?,
        email: String?,
        phoneNumber: String?,
        password: String?,
        id: Long,
        checkValue: Int,
        image: String?,
        uuid: String?
    ) {
        this.name = name
        this.email = email
        this.phoneNumber = phoneNumber
        this.password = password
        this.id = id
        this.checkValue = checkValue
        this.image = image
        this.uuid = uuid
    }

    @Ignore
    constructor() {
    }
}