package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_screen)

        val btnTornarJugar = findViewById(R.id.BtnTornarJugar) as Button
        val btnSortirApp = findViewById(R.id.BtnSortirApp) as Button

        btnTornarJugar.setOnClickListener() {

            val user = "a"

            val intent =  Intent(this, DifficultActivity::class.java)

            startActivity(intent)

        }

        btnSortirApp.setOnClickListener() {

            finishAffinity();

        }
    }
}