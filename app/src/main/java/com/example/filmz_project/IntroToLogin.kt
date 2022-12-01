package com.example.filmz_project

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Timer
import kotlin.concurrent.schedule

class IntroToLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_to_login)

        //audio play
        val mediaPlayerAnimation = MediaPlayer.create(this,R.raw.carga);
        mediaPlayerAnimation.start();
        mediaPlayerAnimation.setLooping(true)

        // Animació de carrèga de LottieFiles
        val introToLoginAnimation = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.introToLoginAnimation)
        introToLoginAnimation.setAnimation(R.raw.introtologin)
        introToLoginAnimation.playAnimation()
        val intent = Intent(this,LoginActivity::class.java)

        //Timer que quant pasin uns segons determinats anirà a una altre activity
        Timer("SettingUp", false).schedule(2500) {
            startActivity(intent)
            mediaPlayerAnimation.stop()
        }

    }
    //desactivamos la funcion de volver
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}