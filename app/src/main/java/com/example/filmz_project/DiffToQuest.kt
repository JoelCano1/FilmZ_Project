package com.example.filmz_project

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class DiffToQuest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_to_quest)

        //audio play
        val mediaPlayerEfecto = MediaPlayer.create(this,R.raw.sonidoagua);
        mediaPlayerEfecto.start();
        mediaPlayerEfecto.setVolume(0.25f, 0.25f)
        mediaPlayerEfecto.setLooping(true)

        val animation = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.diffToQuest)
        animation.setAnimation(R.raw.progressblue)
        animation.playAnimation()

        val intent = getIntent()
        var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User

        val intentTo = Intent(this,QuestionActivity::class.java)
        intentTo.putExtra(Keys.constKeys.DIFFICULT_TO_QUIZ, jugadorActual)

        Timer("SettingUp", false).schedule(3500) {
            startActivity(intentTo)
            //audio stop
            mediaPlayerEfecto.stop()
        }
    }
    //desactivamos la funcion de volver
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}