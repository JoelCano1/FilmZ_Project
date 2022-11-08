package com.example.filmz_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking_screen)

        val linearRanking = findViewById<LinearLayout>(R.id.linearRanking)
        val btnContinuarRanking = findViewById<Button>(R.id.btnContinuarRanking)
        val animation : Animation = AnimationUtils.loadAnimation(this,R.anim.move_down)
        linearRanking.startAnimation(animation)
        btnContinuarRanking.startAnimation(animation)

    }

}