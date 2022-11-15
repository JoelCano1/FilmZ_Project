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

        val intent = getIntent()
        
        btnTornarJugar.setOnClickListener() {

            val user = intent.getSerializableExtra(Keys.constKeys.RANKING_TO_FINAL) as User
            user.puntuacio = 0;

            val intent =  Intent(this, DifficultActivity::class.java)

            startActivity(intent)

        }

        btnSortirApp.setOnClickListener() {

            finishAffinity();

        }
    }
}