package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DifficultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficult_screen)

        val intentLogin = getIntent()

        var user = intentLogin.getSerializableExtra(Keys.constKeys.LOGIN_TO_DIFFICULTY) as User

        seleccioNivell(user)
    }
    private fun seleccioNivell(user:User) {
        val btnFacil = findViewById<Button>(R.id.BtnEasy)
        val btnMitja = findViewById<Button>(R.id.BtnMedium)
        val btnDificil = findViewById<Button>(R.id.BtnDificult)
        val lblDescripcioDificultat = findViewById<TextView>(R.id.LblDescDif)

        btnFacil.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatFacil)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnFacil.backgroundTintList = this.getColorStateList(R.color.verd)
            user.difficult = 1
            continuar(user)
        }
        btnMitja.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatMitja)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnMitja.backgroundTintList = this.getColorStateList(R.color.taronja)
            user.difficult = 2
            continuar(user)
        }
        btnDificil.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText((R.string.difficult_screen_textDificultatDificil))
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnDificil.backgroundTintList = this.getColorStateList(R.color.vermell)
            user.difficult = 3
            continuar(user)
        }
        continuar(user)
    }
    private fun continuar(user: User) {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarDificultat)

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra(Keys.constKeys.DIFFICULT_TO_QUIZ, user)
            startActivity(intent)
        }
    }
    private fun oscurecerButtons(btnFacil: Button, btnMitja: Button, btnDificil: Button) {
        btnFacil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnMitja.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnDificil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
    }
}