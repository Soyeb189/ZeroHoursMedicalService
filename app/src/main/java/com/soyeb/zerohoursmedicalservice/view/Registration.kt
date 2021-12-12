package com.soyeb.zerohoursmedicalservice.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bd.ehaquesoft.sweetalert.SweetAlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.soyeb.zerohoursmedicalservice.R
import com.soyeb.zerohoursmedicalservice.data_model.RegistrationResponseM
import com.soyeb.zerohoursmedicalservice.request_model.RegistrationRequestM
import com.soyeb.zerohoursmedicalservice.util.Custom_alert
import com.soyeb.zerohoursmedicalservice.view_model.RegistrationViewM

class Registration : AppCompatActivity() {

    private lateinit var loginLink: TextView

    private lateinit var doctorLink: TextView

    private lateinit var edtEmailReg: TextInputEditText
    private lateinit var edtNameReg: TextInputEditText
    private lateinit var edtPassReg: TextInputEditText
    private lateinit var edtConfirmPassReg: TextInputEditText
    private lateinit var edtPhoneReg: TextInputEditText

    private lateinit var btnSignUpPatient: Button

    private lateinit var pDialog: SweetAlertDialog
    private lateinit var registrationVM: RegistrationViewM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        loginLink = findViewById(R.id.loginLink)
        doctorLink = findViewById(R.id.doctorLink)
        edtEmailReg = findViewById(R.id.edtEmailReg)
        edtNameReg = findViewById(R.id.edtNameReg)
        edtPhoneReg = findViewById(R.id.edtPhoneReg)
        edtPassReg = findViewById(R.id.edtPassReg)
        edtConfirmPassReg = findViewById(R.id.edtConfirmPassReg)
        btnSignUpPatient = findViewById(R.id.btnSignUpPatient)

        pDialog = Custom_alert.showProgressDialog(this)
        registrationVM = ViewModelProvider(this).get(RegistrationViewM::class.java)

        loginLink.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }

        doctorLink.setOnClickListener {
            var i = Intent(this, DoctorRegistration::class.java)
            startActivity(i)
        }

        btnSignUpPatient.setOnClickListener {
            doRegistration()
        }

        registrationObserver()
    }

    private fun doRegistration() {
        val model = RegistrationRequestM()

        model.email = "" + edtEmailReg.text.toString()
        model.password = "" + edtPassReg.text.toString()
        model.name = "" + edtNameReg.text.toString()
        model.doctor = "0"
        model.reg_no = "1"
        model.password_confirmation = "" + edtConfirmPassReg.text.toString()
        model.phone_no = "" + edtPhoneReg.text.toString()

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