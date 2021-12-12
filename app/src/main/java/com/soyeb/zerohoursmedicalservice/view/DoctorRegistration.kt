package com.soyeb.zerohoursmedicalservice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.RegistrationResponseM
import com.soyeb.zerohoursmedicalservice.request_model.RegistrationRequestM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.view_model.RegistrationViewM

class DoctorRegistration : AppCompatActivity() {

    private lateinit var loginLinkDoctor: TextView
    

    private lateinit var edtEmailDocReg: TextInputEditText
    private lateinit var edtNameDocReg: TextInputEditText
    private lateinit var edtRegNumDocReg: TextInputEditText
    private lateinit var edtPassDocReg: TextInputEditText
    private lateinit var edtConfirmPassDocReg: TextInputEditText
    private lateinit var edtPhoneDocReg: TextInputEditText

    private lateinit var btnDoctorSignUp: Button

    private lateinit var pDialog: SweetAlertDialog
    private lateinit var registrationVM: RegistrationViewM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_registration)

        loginLinkDoctor = findViewById(R.id.loginLinkDoctor)
        
        edtEmailDocReg = findViewById(R.id.edtEmailDocReg)
        edtRegNumDocReg = findViewById(R.id.edtRegNumDocReg)
        edtNameDocReg = findViewById(R.id.edtNameDocReg)
        edtPhoneDocReg = findViewById(R.id.edtPhoneDocReg)
        edtPassDocReg = findViewById(R.id.edtPassDocReg)
        edtConfirmPassDocReg = findViewById(R.id.edtConfirmPassDocReg)
        btnDoctorSignUp = findViewById(R.id.btnDoctorSignUp)

        pDialog = Custom_alert.showProgressDialog(this)
        registrationVM = ViewModelProvider(this).get(RegistrationViewM::class.java)

        loginLinkDoctor.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }

        btnDoctorSignUp.setOnClickListener {
            doRegistration()
        }

        registrationObserver()

    }

    private fun doRegistration() {
        val model = RegistrationRequestM()

        model.email = "" + edtEmailDocReg.text.toString()
        model.password = "" + edtPassDocReg.text.toString()
        model.name = "" + edtNameDocReg.text.toString()
        model.doctor = "1"
        model.reg_no = ""+edtRegNumDocReg.text.toString()
        model.password_confirmation = "" + edtConfirmPassDocReg.text.toString()
        model.phone_no = "" + edtPhoneDocReg.text.toString()

        this.let { registrationVM.doRegistration(model, it) }

        pDialog.show()
    }

    private fun registrationObserver() {
        registrationVM.login.observe(
            this,
            {
                pDialog.dismiss()
                it?.let {
                    pDialog.dismiss()

                    if (it.error.equals("true")) {
                        Custom_alert.showErrorMessage(this, it.message)
                    } else {
                        val model = RegistrationResponseM(
                            it.name,
                            it.phone_no,
                            it.email,
                            it.doctor,
                            it.reg_no,
                            it.updated_at,
                            it.created_at,
                            it.id,
                            it.message,
                            it.error
                        )

                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("রেজিস্ট্রেশান সম্পূর্ণ হয়েছে")
                            .setConfirmText("ওকে")
                            .setConfirmClickListener {
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .show()


                    }


                }
            }
        )
    }
}