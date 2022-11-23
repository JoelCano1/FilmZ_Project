package com.example.filmz_project

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.os.ConfigurationCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_screen)

        val intent = getIntent()
        val jugadorActual = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT) as User
        val encertsGenere = intent.getIntArrayExtra(Keys.constKeys.QUESTIONS_TO_RESULT2) as Array<Int>

        omplirCamps(jugadorActual, encertsGenere)

        // ↓ SABER EL CURRENT LANGUAGE DE LA APP ↓ //
        var lang = resources.getConfiguration().locale.getLanguage()
        //=========================================//
        val personatges: MutableList<Personatge>

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
    private fun omplirCamps(jugadorActual: User, encertsGenere: Array<Int>) {
        //POSAR NOM
        val lblNomUser = findViewById<TextView>(R.id.LblUserName)
        lblNomUser.text = jugadorActual.nom

        //POSAR ENCERTS TOTALS
        val lblEncertsTotals = findViewById<TextView>(R.id.LblEncertsTotals)
        val sumEncerts = encertsGenere.sum();
        lblEncertsTotals.text = "$sumEncerts/20"

        //POSAR ENCERTS PER GENERE
        val lstEncerts = findViewById<ListView>(R.id.LstStats)
        //val adapter = ResultAdapter(this, R.layout.result_item, encertsGenere)
        //lstEncerts.adapter = adapter
    }
    private fun posarPersonatge(personatgesCatala: MutableList<Personatge>) {
        val imgPersonatgeImatge = findViewById<ImageView>(R.id.ImgPersonatge)
        val lblDescripcioPersonatge = findViewById<TextView>(R.id.LblDescPersonatge)
        //Saber resultats dusuari (CATEGORIA I ENCERTS) --> Agafar de la llista personatges

        imgPersonatgeImatge.setImageResource(Integer.parseInt(personatgesCatala.get(1).rutaPers))
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