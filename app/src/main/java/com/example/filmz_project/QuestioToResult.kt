package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import kotlin.concurrent.schedule

class QuestioToResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questio_to_result)

        val animation = findViewById<com.airbnb.lottie.LottieAnimationView>(R.id.questToResult)
        animation.setAnimation(R.raw.plane)
        animation.playAnimation()

        val intent = getIntent()
        val jugadorActual = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT) as User
        val encertsGenere = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT2) as Array<Int>
        //val correctCategory = arrayOf(1, 2, 3, 4, 5)
        //a

        val intentTo = Intent(this,ResultActivity::class.java)
        intentTo.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT, jugadorActual)
        intentTo.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT2, encertsGenere)



            Timer("SettingUp", false).schedule(3000) {
            startActivity(intentTo)
        }
    }
}