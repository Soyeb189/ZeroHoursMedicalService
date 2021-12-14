package com.soyeb.zerohoursmedicalservice.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceUtility private constructor() {


    fun setUserName(context: Context, userName: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
    }


    fun getUserName(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(USER_NAME, DEFAULT_EMPTY_TOKEN)
    }

    fun setUserPhone(context: Context, phone: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(USER_PHONE, phone)
        editor.apply()
    }

    fun getUserPhone(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(USER_PHONE, DEFAULT_EMPTY_TOKEN)
    }

    fun setDoctor(context: Context, doctor: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(DOCTOR, doctor)
        editor.apply()
    }


    fun getDoctor(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(DOCTOR, DEFAULT_EMPTY_TOKEN)
    }



    fun setApprove(context: Context, approve: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(APPROVE, approve)
        editor.apply()
    }


    fun getApprove(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(APPROVE, DEFAULT_EMPTY_TOKEN)
    }


    fun setUserId(context: Context, uId: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(USER_ID, uId)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(USER_ID, DEFAULT_EMPTY_TOKEN)
    }

    fun setUserEmail(context: Context, email: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(USER_EMAIL, email)
        editor.apply()
    }


    fun getUserEmail(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(USER_EMAIL, DEFAULT_EMPTY_TOKEN)
    }

    fun setIsLogin(context: Context, login: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(LOGIN, login)
        editor.apply()
    }


    fun getIsLogin(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(LOGIN, DEFAULT_EMPTY_TOKEN)
    }

    fun setIsDoctor(context: Context, login: String?) {
        val editor = getPreferences(context)!!
            .edit()
        editor.putString(IS_DOCTOR, login)
        editor.apply()
    }


    fun getIsDoctor(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences!!.getString(IS_DOCTOR, DEFAULT_EMPTY_TOKEN)
    }




    companion object {


        private const val USER_NAME = ""
        private const val USER_PHONE = ""
        private const val DOCTOR = "0"
        private const val IS_DOCTOR = "0"
        private const val APPROVE = "0"
        private const val USER_EMAIL = "user_email"
        private const val USER_ID = "user_id"
        private const val DEFAULT_EMPTY_TOKEN = ""
        private const val LOGIN = "0"

        private val LOCK = Any()
        private var sInstance: SharedPreferences? = null
        val instance: PreferenceUtility
            get() = PreferenceUtility()

        private fun getPreferences(context: Context): SharedPreferences? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = PreferenceManager.getDefaultSharedPreferences(context)
                }
            }
            return sInstance
        }
    }
}