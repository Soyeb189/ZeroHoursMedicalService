package com.soyeb.zerohoursmedicalservice.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.LoginResponseM
import com.soyeb.zerohoursmedicalservice.data_model.UserDetails
import com.soyeb.zerohoursmedicalservice.local.User
import com.soyeb.zerohoursmedicalservice.local.UserDataBase
import com.soyeb.zerohoursmedicalservice.request_model.LoginRequestM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.view_model.LoginViewM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var regLink: TextView

    private lateinit var loginVM: LoginViewM
    private lateinit var pDialog: SweetAlertDialog

    private lateinit var edtPhoneLogin : TextInputEditText
    private lateinit var edtPasswordLogin : TextInputEditText

    private lateinit var globalVariable: GlobalVariable

    private val user: User? = null

    lateinit var userDetails: UserDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialization()

        btnLogin = findViewById(R.id.btnLogin)
        regLink = findViewById(R.id.regLink)

        btnLogin.setOnClickListener {
            doLogin()
        }

        regLink.setOnClickListener {
            var i = Intent(this, Registration::class.java)
            startActivity(i)
        }

        loginObserver()

    }

    private fun initialization() {
        btnLogin = findViewById(R.id.btnLogin)
        regLink = findViewById(R.id.regLink)

        edtPhoneLogin = findViewById(R.id.edtPhoneLogin)
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin)

        //********* View Model **********//
        loginVM = ViewModelProvider(this).get(LoginViewM::class.java)

        //********* Alert Dialog ***********//
        pDialog = Custom_alert.showProgressDialog(this)

        globalVariable = this.applicationContext as GlobalVariable
    }


    private fun doLogin(){
        val model = LoginRequestM()

        model.phone_no = ""+edtPhoneLogin.text.toString()
        model.password = ""+edtPasswordLogin.text.toString()

        this.let { loginVM.doLogin(model,it) }

        pDialog.show()
    }

    private fun loginObserver(){
        loginVM.login.observe(
            this,
            {
                pDialog.dismiss()
                it?.let {
                    pDialog.dismiss()

                    if (it.error?.equals("true")!!){
                        Custom_alert.showErrorMessage(this,it.message)
                    }else{
                        val model = LoginResponseM(
                            it.id ,
                            it.name,
                            it.email,
                            it.phoneNo,
                            it.emailVerifiedAt ,
                            it.image,
                            it.admin,
                            it.doctor,
                            it.approve ,
                            it.regNo,
                            it.createdAt,
                            it.updatedAt,
                            it.message,
                            it.error
                        )



                        if (model.error?.equals("false")!!){

                          /*  userDetails = UserDetails(this)

                            CoroutineScope(Dispatchers.IO).launch {
                                userDetails.storeUser(
                                    model.name,
                                    model.doctor
                                )
                            }*/

                            PreferenceUtility.instance.setUserId(this,model.id.toString())
                            Log.d("SSS","ID"+model.id.toString())
                            PreferenceUtility.instance.setUserName(this,model.name.toString())
                            Log.d("SSS","Name"+model.name.toString())
                            PreferenceUtility.instance.setUserEmail(this,model.email.toString())
                            Log.d("SSS","Email"+model.email.toString())
                            PreferenceUtility.instance.setDoctor(this,model.doctor.toString())
                            Log.d("SSS","Doctor"+model.doctor.toString())
                            PreferenceUtility.instance.setApprove(this,model.approve.toString())
                            Log.d("SSS","Approve"+model.approve.toString())
                            PreferenceUtility.instance.setIsDoctor(this,"0")
                            PreferenceUtility.instance.setUserPhone(this,model.phoneNo.toString())

                            saveSharedData(model.id.toString(),model.name,model.email,model.phoneNo,model.doctor,model.approve)



                            globalVariable.id = model.id.toString()
                            globalVariable.doctor = model.doctor
                            globalVariable.name = model.name
                            globalVariable.email = model.email

                            Log.e("SSSS","IDঃ "+PreferenceUtility.instance.getIsDoctor(this))
                            Log.d("SSS","ID"+model.id.toString())

                            if (model.doctor?.equals("1")!! && model.approve?.equals("0")!!){

                                SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("You are not approved yet")
                                    .show()
                            }else if (model.doctor?.equals("1")!! && model.approve?.equals("1")!!){

                                PreferenceUtility.instance.setIsLogin(this,"1")

                                SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("লগইন সম্পূর্ণ হয়েছে")
                                    .setConfirmText("ওকে")
                                    .setConfirmClickListener {
                                        val intent = Intent(this, Home::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .show()

                            }else if (model.doctor?.equals("0")!! && model.approve?.equals("0")!!){

                                PreferenceUtility.instance.setIsLogin(this,"1")

                                SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("লগইন সম্পূর্ণ হয়েছে")
                                    .setConfirmText("ওকে")
                                    .setConfirmClickListener {
                                        val intent = Intent(this, Home::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .show()
                            }
                            
                        }
                    }


                }
            }
        )
    }

    private fun saveSharedData(id:String,name:String,email:String,phone:String,doctor: String,approve:String) {
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPref",Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("ID",id)
            putString("NAME",name)
            putString("EMAIL",email)
            putString("PHONE",phone)
            putString("DOCTOR",doctor)
            putString("APPROVE",approve)
        }.apply()
    }
}