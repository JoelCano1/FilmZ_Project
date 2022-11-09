package com.example.filmz_project

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Difficult : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficult_screen)

        val btnFacil = findViewById<Button>(R.id.BtnEasy)
        val btnMitja = findViewById<Button>(R.id.BtnMedium)
        val btnDificil = findViewById<Button>(R.id.BtnDificult)
        val lblDesc = findViewById<TextView>(R.id.LblDesc)

        btnFacil.setOnClickListener()
        {
            lblDesc.text = "FACIL"
        }

        btnMitja.setOnClickListener()
        {
            lblDesc.text = "MEDIUM"
        }

        btnDificil.setOnClickListener()
        {
            lblDesc.text = "DIFICIL"
        }
    }
}