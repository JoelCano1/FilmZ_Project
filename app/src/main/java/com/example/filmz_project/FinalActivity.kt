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

        val intetToGet = getIntent()

        btnTornarJugar.setOnClickListener() {
            val user = intetToGet.getSerializableExtra(Keys.constKeys.TO_FINAL) as User

            val intent =  Intent(this, DifficultActivity::class.java)
            intent.putExtra(Keys.constKeys.TO_DIFFICULT, user)
            intent.putExtra(Keys.constKeys.AUDIO_LOGIN, 0)
            startActivity(intent)
        }

        btnSortirApp.setOnClickListener() {
            finishAffinity();
        }
    }
}