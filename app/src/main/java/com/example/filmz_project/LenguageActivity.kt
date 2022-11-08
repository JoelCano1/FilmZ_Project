package com.example.filmz_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout

class LenguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lenguage_screen)


        val spanishFlag = findViewById(R.id.BtnCastellano) as ImageView

        spanishFlag.animate().apply {

            duration = 1500
            alpha(1f)
            rotationYBy(180f)
        }.start()
    }
}