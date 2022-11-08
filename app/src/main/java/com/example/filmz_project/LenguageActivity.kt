package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class LenguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()
    }

    private fun load(){
        setContentView(R.layout.lenguage_screen)

        val btnCat = findViewById<ImageView>(R.id.BtnCatala)
        val btnEsp = findViewById<ImageView>(R.id.BtnCastellano)
        val btnEng = findViewById<ImageView>(R.id.BtnEnglish)
        val lblText = findViewById<TextView>(R.id.LblIdioma)

        btnCat.setOnClickListener()
        {
            setLocale("ca", "ES")
            lblText.text = resources.getText(R.string.text_idioma)
        }
        btnEsp.setOnClickListener()
        {
            setLocale("es", "ES")
            lblText.text = resources.getText(R.string.text_idioma)
        }
        btnEng.setOnClickListener()
        {
            setLocale("en", "US")
            lblText.text = resources.getText(R.string.text_idioma)
        }

        continuar()
    }

    private fun setLocale(lang: String, loc: String)
    {
        val locale = Locale(lang, loc)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun continuar() {
        val btnContinuar = findViewById<ImageButton>(R.id.BtnContinuarLanguage)

        btnContinuar.setOnClickListener(){
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}