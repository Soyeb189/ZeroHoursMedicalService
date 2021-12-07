package com.soyeb.zerohoursmedicalservice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.soyeb.zerohoursmedicalservice.R

class Registration : AppCompatActivity() {

    private lateinit var loginLink : TextView

    private lateinit var doctorLink : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        loginLink = findViewById(R.id.loginLink)
        doctorLink = findViewById(R.id.doctorLink)

        loginLink.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }

        doctorLink.setOnClickListener {
            var i = Intent(this, DoctorRegistration::class.java)
            startActivity(i)
        }
    }
}