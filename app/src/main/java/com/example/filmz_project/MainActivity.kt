package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGoIntro = findViewById<Button>(R.id.btnGoIntro)
        val btnGoDifficult = findViewById<Button>(R.id.btnGoDifficult)
        val btnGoFinal = findViewById<Button>(R.id.btnGoFinal)
        val btnGoLenguage = findViewById<Button>(R.id.btnGoLenguage)
        val btnGoLogin = findViewById<Button>(R.id.btnGoLogin)
        val btnGoLogo = findViewById<Button>(R.id.btnGoLogo)
        val btnGoQuestions = findViewById<Button>(R.id.btnGoQuestions)
        val btnGoRanking = findViewById<Button>(R.id.btnGoRanking)
        val btnGoRegister = findViewById<Button>(R.id.btnGoRegister)
        val btnGoResult = findViewById<Button>(R.id.btnGoResult)

        /*------ POSEM L'IDIOMA DEFAULT COM A CATALÀ ------*/
        val locale = Locale("ca", "ES")
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        /*-------------------------------------------------*/

        btnGoIntro.setOnClickListener(){
            val intent = Intent(this,IntroActivity::class.java)
            startActivity(intent)
        }
        btnGoDifficult.setOnClickListener(){
            val intent = Intent(this,DifficultActivity::class.java)
            startActivity(intent)
        }
        btnGoFinal.setOnClickListener(){
            val intent = Intent(this,FinalActivity::class.java)
            startActivity(intent)
        }
        btnGoLenguage.setOnClickListener(){
            val intent = Intent(this,LenguageActivity::class.java)
            startActivity(intent)
        }
        btnGoLogin.setOnClickListener(){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        btnGoQuestions.setOnClickListener(){
            val intent = Intent(this,QuestionActivity::class.java)
            startActivity(intent)
        }
        btnGoRanking.setOnClickListener(){
            val intent = Intent(this,RankingActivity::class.java)
            startActivity(intent)
        }
        btnGoRegister.setOnClickListener(){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        btnGoResult.setOnClickListener(){
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
        }
        btnGoLogo.setOnClickListener(){
            val intent = Intent(this,LogoActivity::class.java)
            startActivity(intent)
        }
    }
}