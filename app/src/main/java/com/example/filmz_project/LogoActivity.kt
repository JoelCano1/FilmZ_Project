package com.example.filmz_project

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logo_screen)
        val imgLogo = findViewById<ImageView>(R.id.imgLogo)
        /*------ POSEM L'IDIOMA DEFAULT COM A CATALÀ ------*/
        val locale = Locale("ca", "ES")
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        /*-------------------------------------------------*/
        
        //audio play
        val mediaPlayerLogo = MediaPlayer.create(this,R.raw.intrologo);
        mediaPlayerLogo.start();
        mediaPlayerLogo.setLooping(true)

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
                        //audio stop
                        mediaPlayerLogo.stop()
                    })
            })
    }
}