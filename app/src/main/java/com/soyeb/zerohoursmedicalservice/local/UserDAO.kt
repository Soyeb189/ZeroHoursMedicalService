package com.soyeb.zerohoursmedicalservice.local

import androidx.room.*

@Dao
interface UserDAO {
    @Insert
    fun addUser(user: User?): Long

    @Update
    fun updateUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @Query("SELECT * FROM User where user_email= :mail and user_password= :password")
    fun getUser(mail: String?, password: String?): User?

    @Query("SELECT * FROM User where user_email = :email")
    fun getSelectedUser(email: String?): User?

    @Query("UPDATE user SET user_name = :name , user_email = :email WHERE user_id = :u_id")
    fun updateSelected(name: String?, email: String?, u_id: Long)

    @Query("SELECT * FROM User Where device_uuid = :d_uuid and checked_val = :m")
    fun getCheckedVal(d_uuid: String?, m: Int): User?
}