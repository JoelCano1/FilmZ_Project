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
        val nivell : Int

        nivell = eleccioNivell()

        continuar(nivell)
    }
    private fun eleccioNivell(): Int {
        var nivell : Int = 2
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
    private fun continuar(nivell: Int) {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarDificultat)
        //PASAR NIVELL AMB PUT_EXTRA

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
    private fun oscurecerButtons(btnFacil: Button, btnMitja: Button, btnDificil: Button) {
        btnFacil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnMitja.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnDificil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
    }
}