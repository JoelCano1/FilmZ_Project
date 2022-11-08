package com.example.filmz_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class LenguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lenguage_screen)

        //animacion
        val animation1: Animation = AnimationUtils.loadAnimation(this, R.anim.flip_flag)
        val animation2: Animation = AnimationUtils.loadAnimation(this, R.anim.flip_flag)
        val spanishFlag = findViewById(R.id.BtnCastellano) as ImageView
        val catalanFlag = findViewById(R.id.BtnCatala) as ImageView
        val englishFlag = findViewById(R.id.BtnEnglish) as ImageView
        val lblLenguaje = findViewById(R.id.lblLenguaje) as TextView
        spanishFlag.startAnimation(animation1)
        catalanFlag.startAnimation(animation1)
        englishFlag.startAnimation(animation1)
        lblLenguaje.startAnimation(animation2)


    }
}