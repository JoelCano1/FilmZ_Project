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

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_screen)

        val intent = getIntent()
        val jugadorActual = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT) as User
        val encertsGenere = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT2) as Array<Int>

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

        posarPersonatge(personatges, encertsGenere)
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
        posarNom(jugadorActual)

        //POSAR ENCERTS TOTALS
        posarEncertsTotals(encertsGenere)

        //POSAR ENCERTS PER GENERE
        posarEncertsGenere(encertsGenere)
    }

    private fun posarNom(jugadorActual: User) {
        val lblNomUser = findViewById<TextView>(R.id.LblUserName)
        lblNomUser.text = jugadorActual.nom
    }

    private fun posarEncertsTotals(encertsGenere: Array<Int>) {
        val lblEncertsTotals = findViewById<TextView>(R.id.LblEncertsTotals)
        val sumEncerts = encertsGenere.sum();
        lblEncertsTotals.text = "$sumEncerts/20"
    }

    private fun posarEncertsGenere(encertsGenere: Array<Int>) {
        val MAX_PREGUNTES_GENERE = 4
        val lblEncertsDrama = findViewById<TextView>(R.id.LblEncertsDrama)
        val lblEncertsTerror = findViewById<TextView>(R.id.LblEncertsTerror)
        val lblEncertsAnimacio = findViewById<TextView>(R.id.LblEncertsAnimacio)
        val lblEncertsAccio = findViewById<TextView>(R.id.LblEncertsAccio)
        val lblEncertsCienciaFiccio = findViewById<TextView>(R.id.LblEncertsCienciaFiccio)
        lblEncertsDrama.text = "${encertsGenere[0]}/$MAX_PREGUNTES_GENERE"
        lblEncertsTerror.text = "${encertsGenere[1]}/$MAX_PREGUNTES_GENERE"
        lblEncertsAnimacio.text = "${encertsGenere[2]}/$MAX_PREGUNTES_GENERE"
        lblEncertsAccio.text = "${encertsGenere[3]}/$MAX_PREGUNTES_GENERE"
        lblEncertsCienciaFiccio.text = "${encertsGenere[4]}/$MAX_PREGUNTES_GENERE"
    }

    private fun posarPersonatge(personatges: MutableList<Personatge>, encertsGenere: Array<Int>) {
        algoritmeSaberPersonatge(personatges, encertsGenere)
        val imgPersonatgeImatge = findViewById<ImageView>(R.id.ImgPersonatge)
        val lblDescripcioPersonatge = findViewById<TextView>(R.id.LblDescPersonatge)
        //Saber resultats dusuari (CATEGORIA I ENCERTS) --> Agafar de la llista personatges

        //imgPersonatgeImatge.drawable = getFilesDir().toString() + ""
        lblDescripcioPersonatge.text = personatges.get(1).descripcioPers
    }

    private fun algoritmeSaberPersonatge(personatges: MutableList<Personatge>, encertsGenere: Array<Int>) {
        val totsIgualMalamanet = encertsGenere.toSet().size == 0
        if (totsIgualMalamanet == false) {
            val totsIgualBe = encertsGenere.toSet().size == 4
            if (totsIgualBe == false) {
                var millorGenere = 0
                for (i: Int in encertsGenere) {
                    if (encertsGenere[i] > encertsGenere[millorGenere]) {
                        millorGenere = i
                    }
                }
            } else {
                println("tots estan be agafar el TOT BÉ de personatges")
            }
        } else {
            println("tots estan malament agafar el CAP de personatges")
        }
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