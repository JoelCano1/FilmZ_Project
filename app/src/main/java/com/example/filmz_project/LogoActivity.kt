package com.example.filmz_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_screen)
        val imgLogo = findViewById<ImageView>(R.id.imgLogo)

        imgLogo.animate()
            .alpha(1f)
            .setStartDelay(1000)
            .setDuration(2000)
            .withEndAction(Runnable
            {
                imgLogo.animate()
                    .alpha(0f)
                    .setDuration(2000)
                    .withEndAction(Runnable
                    {
                        imgLogo.setVisibility(View.GONE)
                        val intent = Intent(this,LenguageActivity::class.java)
                        startActivity(intent)
                    })
            })





    }
}