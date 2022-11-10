package com.example.filmz_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)

        val timeBar = findViewById(R.id.ProgressBar) as ProgressBar
        val pros = findViewById<TextView>(R.id.NumPreg)
        timeBar.max = 75
        timeBar.progress = 0

        object : CountDownTimer(15_000, 1){
            override fun onTick(remaining: Long) {
                timeBar.progress = (timeBar.progress+1)
                //pros.text = remaining.toString()
            }

            override fun onFinish() {

            }
        }.start()



    }
}