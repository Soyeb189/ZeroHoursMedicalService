package com.soyeb.zerohoursmedicalservice.view

import android.content.Intent
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
import com.soyeb.zerohoursmedicalservice.request_model.LoginRequestM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.util.GlobalVariable
import com.soyeb.zerohoursmedicalservice.util.PreferenceUtility
import com.soyeb.zerohoursmedicalservice.view_model.LoginViewM

class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var regLink: TextView

    private lateinit var loginVM: LoginViewM
    private lateinit var pDialog: SweetAlertDialog

    private lateinit var edtPhoneLogin : TextInputEditText
    private lateinit var edtPasswordLogin : TextInputEditText

    private lateinit var globalVariable: GlobalVariable

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
                            it.phone_no,
                            it.email_verified_at ,
                            it.admin,
                            it.doctor,
                            it.approve ,
                            it.reg_no,
                            it.created_at,
                            it.updated_at,
                            it.message,
                            it.error
                        )



                        if (model.error?.equals("false")!!){

                            PreferenceUtility.instance.setUserId(this,model.id.toString())
                            PreferenceUtility.instance.setUserName(this,model.name.toString())
                            PreferenceUtility.instance.setUserEmail(this,model.email.toString())
                            PreferenceUtility.instance.setDoctor(this,model.doctor.toString())
                            PreferenceUtility.instance.setApprove(this,model.approve.toString())

                            globalVariable.id = model.id.toString()
                            Log.d("SSS","ID"+globalVariable.id)
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





//                            Custom_alert.showSuccessMessage(this,"লগিন সম্পূর্ণ হয়েছে")
//                            pDialog.confirmText = "লগিন সম্পূর্ণ হয়েছে"
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
                        }
                    }


                }
            }
        )
    }
}