package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.util.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_screen)

        val personatges: MutableList<Personatge>
        var lang = "ca"
        if(lang == "ca") {
            personatges = getPersonatgesCatala()
        } else if (lang == "es") {
            personatges = getPersonatgesCastella()
        } else {
            personatges = getPersonatgesAngles()
        }

        posarPersonatge(personatges)
        veureRanking()
        continuar()
    }

    private fun getPersonatgesCatala(): MutableList<Personatge> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/PersCAT.json"
        val jsonFile = FileReader(jsonFilePath)
        val listPersonatgesCatala = object: TypeToken<MutableList<Personatge>>() {}.type
        val personatgesCatala: MutableList<Personatge> = Gson().fromJson(jsonFile, listPersonatgesCatala)
        return personatgesCatala
    }
    private fun getPersonatgesCastella(): MutableList<Personatge> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/PersCAST.json"
        val jsonFile = FileReader(jsonFilePath)
        val listPersonatgesCastella = object: TypeToken<MutableList<Personatge>>() {}.type
        val personatgesCastella: MutableList<Personatge> = Gson().fromJson(jsonFile, listPersonatgesCastella)
        return personatgesCastella
    }
    private fun getPersonatgesAngles(): MutableList<Personatge> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/PersAngles.json"
        val jsonFile = FileReader(jsonFilePath)
        val listPersonatgesAngles = object: TypeToken<MutableList<Personatge>>() {}.type
        val personatgesAngles: MutableList<Personatge> = Gson().fromJson(jsonFile, listPersonatgesAngles)
        return personatgesAngles
    }
    private fun posarPersonatge(personatgesCatala: MutableList<Personatge>) {
        val imgPersonatgeImatge = findViewById<ImageView>(R.id.ImgPersonatge)
        val lblDescripcioPersonatge = findViewById<TextView>(R.id.LblDescPersonatge)
        //Saber resultats dusuari (CATEGORIA I ENCERTS) --> Agafar de la llista personatges

        lblDescripcioPersonatge.text = personatgesCatala.get(1).descripcioPers
    }

    private fun veureRanking() {
        val btnRanking = findViewById<Button>(R.id.BtnRankingResult)

        btnRanking.setOnClickListener() {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun continuar() {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarResult)

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, FinalActivity::class.java)
            startActivity(intent)
        }
    }
}