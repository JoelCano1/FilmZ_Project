package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class DiffToQuest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_to_quest)


        val animation = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.diffToQuest)
        animation.setAnimation(R.raw.progressblue)
        animation.playAnimation()

        val intent = getIntent()
        var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User

        val intentTo = Intent(this,QuestionActivity::class.java)
        intentTo.putExtra(Keys.constKeys.DIFFICULT_TO_QUIZ, jugadorActual)

        Timer("SettingUp", false).schedule(3500) {
            startActivity(intentTo)
        }
    }
}