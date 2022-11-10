package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import java.util.*

class LenguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()
    }

    private fun load(){
        setContentView(R.layout.lenguage_screen)

        //animacion
        val animation1: Animation = AnimationUtils.loadAnimation(this, R.anim.spawn)
        val animation2: Animation = AnimationUtils.loadAnimation(this, R.anim.move_up)
        val animation3: Animation = AnimationUtils.loadAnimation(this, R.anim.move_down2)
        val spanishFlag = findViewById(R.id.BtnCastellano) as ImageView
        val catalanFlag = findViewById(R.id.BtnCatala) as ImageView
        val englishFlag = findViewById(R.id.BtnEnglish) as ImageView
        val lblLenguaje = findViewById(R.id.lblLenguaje) as TextView
        val btnNext = findViewById(R.id.btonNextLeng) as ImageView

        spanishFlag.startAnimation(animation1)
        catalanFlag.startAnimation(animation1)
        englishFlag.startAnimation(animation1)
        lblLenguaje.startAnimation(animation2)
        btnNext.startAnimation(animation3)

        val btnCat = findViewById<ImageView>(R.id.BtnCatala)
        val btnEsp = findViewById<ImageView>(R.id.BtnCastellano)
        val btnEng = findViewById<ImageView>(R.id.BtnEnglish)
        val lblText = findViewById<TextView>(R.id.lblLenguaje)

        btnCat.setOnClickListener()
        {
            setLocale("ca", "ES")
            lblText.text = resources.getText(R.string.text_idioma)
            catalanFlag.setImageResource(R.drawable.cat1)
            englishFlag.setImageResource(R.drawable.eng1black)
            spanishFlag.setImageResource(R.drawable.spain1black)
        }
        btnEsp.setOnClickListener()
        {
            setLocale("es", "ES")
            lblText.text = resources.getText(R.string.text_idioma)
            spanishFlag.setImageResource(R.drawable.spain1)
            catalanFlag.setImageResource(R.drawable.cat1black)
            englishFlag.setImageResource(R.drawable.eng1black)
        }
        btnEng.setOnClickListener()
        {
            setLocale("en", "US")
            lblText.text = resources.getText(R.string.text_idioma)
            englishFlag.setImageResource(R.drawable.eng1)
            spanishFlag.setImageResource(R.drawable.spain1black)
            catalanFlag.setImageResource(R.drawable.cat1black)
        }

        continuar(btnNext)


    }

    private fun setLocale(lang: String, loc: String)
    {
        val locale = Locale(lang, loc)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun continuar(btnNext: ImageView ) {
        btnNext.setOnClickListener(){
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            btnNext.setImageResource(R.drawable.nextbutclick)
            
        }
    }



}