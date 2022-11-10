package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_screen)

        veureRanking()
        continuar()
    }
    private fun veureRanking() {
        val btnRanking = findViewById<Button>(R.id.BtnRankingResult)

        btnRanking.setOnClickListener() {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun continuar() {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarResult)

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, FinalActivity::class.java)
            startActivity(intent)
        }
    }
}