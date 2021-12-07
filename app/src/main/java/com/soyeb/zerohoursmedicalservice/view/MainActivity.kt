package com.soyeb.zerohoursmedicalservice.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDelegate
import com.soyeb.zerohoursmedicalservice.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var timer: Timer
    private  var i = 0
    private lateinit var animator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        progressBar = findViewById(R.id.prgressbar_id)
        animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)

        animator.duration = 3000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //Toast.makeText(this@SplashScreen, "anim", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })


        handler = Handler()
        runnable = Runnable {
            timer.cancel()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, 3500, 3000)

    }
}