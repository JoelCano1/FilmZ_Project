package com.example.filmz_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)
        }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficult_screen)

        val btnFacil = findViewById<Button>(R.id.BtnEasy)
        val btnMitja = findViewById<Button>(R.id.BtnMedium)
        val btnDificil = findViewById<Button>(R.id.BtnDificult)
        val lblDesc = findViewById<TextView>(R.id.LblDesc)

        btnFacil.setOnClickListener()
        {
            lblDesc.text =
        }

        btnMitja.setOnClickListener()
        {
            lblDesc.text = "@strings/mitja"
        }

        btnDificil.setOnClickListener()
        {
            lblDesc.text = "@strings/dificil"
        }
    }

}