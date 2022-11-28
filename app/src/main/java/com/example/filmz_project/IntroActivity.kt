package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button

class IntroActivity() : AppCompatActivity() {

    object constants{
        const val minuts = 4
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_screen)

      val btnTempsRestant = findViewById<Button>(R.id.btnTempsRestant)
      var tempsSegons = IntroActivity.constants.minuts.toLong()
      var tempsMilisegons = tempsSegons * 1000
      val intent = Intent(this,IntroToLogin::class.java)

      object: CountDownTimer(tempsMilisegons, 1000){
          override fun onTick(millisUntilFinished: Long) {
              val tempsSegons = (millisUntilFinished/1000).toInt()+1
              val string: String = getString(R.string.intro_screen_skip)
              btnTempsRestant.text = string +" "+ tempsSegons.toString()+"''"
          }

          override fun onFinish() {
              btnTempsRestant.alpha= 1F
              val string: String = getString(R.string.intro_screen_continuar)
              btnTempsRestant.text = string

              btnTempsRestant.setOnClickListener(){
                  startActivity(intent)
              }
          }
      }.start()
    }
}