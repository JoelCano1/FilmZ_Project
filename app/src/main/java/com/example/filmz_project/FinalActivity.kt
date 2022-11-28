package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FinalActivity : AppCompatActivity() {

    /**
     * @author Albert
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_screen)

        //Declarem totes les variables de les views que es faran servir a aquesta activity
        val btnTornarJugar = findViewById(R.id.BtnTornarJugar) as Button
        val btnSortirApp = findViewById(R.id.BtnSortirApp) as Button

        //Rebem un intent de l'activity antertior
        val intetToGet = getIntent()

        //En cas que li dongui a tornar a jugar enviem a l'usuari a l'activity per esollir la dificultat
        btnTornarJugar.setOnClickListener() {

            //Carreguem una variable user que conté les dades de l'usuariq ue està jugant
            val user = intetToGet.getSerializableExtra(Keys.constKeys.RANKING_TO_FINAL) as User

            //Generem un intent per tornar a la pàgina dificulty i enviem també l'usuari que està jugant
            val intent =  Intent(this, DifficultActivity::class.java)
            intent.putExtra(Keys.constKeys.LOGIN_TO_DIFFICULTY, user)
            startActivity(intent)

        }

        //En cas que li dongui a sortir l'aplicació es tancarà
        btnSortirApp.setOnClickListener() {

            finishAffinity();

        }
    }
}