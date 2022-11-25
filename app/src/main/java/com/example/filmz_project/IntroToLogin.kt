package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Timer
import kotlin.concurrent.schedule

class IntroToLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_to_login)

        val introToLoginAnimation = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.introToLoginAnimation)
        introToLoginAnimation.setAnimation(R.raw.introtologin)
        introToLoginAnimation.playAnimation()
        val intent = Intent(this,LoginActivity::class.java)

        Timer("SettingUp", false).schedule(2500) {
            startActivity(intent)
        }


    }
}