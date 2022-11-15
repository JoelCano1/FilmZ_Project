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

        val intent = getIntent()

        var user = intent.getSerializableExtra(Keys.constKeys.LOGIN_TO_DIFFICULTY) as User

        user.difficult = eleccioNivell()

        continuar(user)
    }
    private fun eleccioNivell(): Int {
        var nivell = -1
        val btnFacil = findViewById<Button>(R.id.BtnEasy)
        val btnMitja = findViewById<Button>(R.id.BtnMedium)
        val btnDificil = findViewById<Button>(R.id.BtnDificult)
        val lblDescripcioDificultat = findViewById<TextView>(R.id.LblDescDif)

        btnFacil.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatFacil)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnFacil.backgroundTintList = this.getColorStateList(R.color.verd)
            nivell = 1
        }
        btnMitja.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatMitja)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnMitja.backgroundTintList = this.getColorStateList(R.color.taronja)
            nivell = 2
        }
        btnDificil.setOnClickListener() {
            lblDescripcioDificultat.text = resources.getText((R.string.difficult_screen_textDificultatDificil))
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnDificil.backgroundTintList = this.getColorStateList(R.color.vermell)
            nivell = 3
        }
        return nivell
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