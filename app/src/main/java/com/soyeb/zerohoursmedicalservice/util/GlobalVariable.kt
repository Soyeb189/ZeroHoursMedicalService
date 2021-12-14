package com.soyeb.zerohoursmedicalservice.util

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class GlobalVariable : Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    var id : String?=""
    var name : String?=""
    var email : String?=""
    var doctor : String ? = ""

    var context: Context? = null
}