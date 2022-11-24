package com.example.filmz_project

import android.content.Intent
import android.graphics.BitmapFactory
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
        val encertsGenere =
            intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT2) as Array<Int>

        omplirCamps(jugadorActual, encertsGenere)

        // ↓ SABER EL CURRENT LANGUAGE DE LA APP ↓ //
        var lang = resources.getConfiguration().locale.getLanguage()
        //=========================================//
        val personatges: MutableList<Personatge>

        if (lang == "ca") {
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
        val listPersonatgesCatala = object : TypeToken<MutableList<Personatge>>() {}.type
        val personatgesCatala: MutableList<Personatge> =
            Gson().fromJson(jsonFile, listPersonatgesCatala)
        return personatgesCatala
    }

    private fun getPersonatgesCastella(): MutableList<Personatge> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/PersCAST.json"
        val jsonFile = FileReader(jsonFilePath)
        val listPersonatgesCastella = object : TypeToken<MutableList<Personatge>>() {}.type
        val personatgesCastella: MutableList<Personatge> =
            Gson().fromJson(jsonFile, listPersonatgesCastella)
        return personatgesCastella
    }

    private fun getPersonatgesAngles(): MutableList<Personatge> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/PersAngles.json"
        val jsonFile = FileReader(jsonFilePath)
        val listPersonatgesAngles = object : TypeToken<MutableList<Personatge>>() {}.type
        val personatgesAngles: MutableList<Personatge> =
            Gson().fromJson(jsonFile, listPersonatgesAngles)
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
        val lblNomUser = findViewById<TextView>(R.id.LblNomUser)
        lblNomUser.text = jugadorActual.nom
    }

    private fun posarEncertsTotals(encertsGenere: Array<Int>) {
        val lblEncertsTotals = findViewById<TextView>(R.id.LblEncertsTotal)
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
        val pos = algoritmeSaberPersonatge(personatges, encertsGenere)
        val imgPersonatgeImatge = findViewById<ImageView>(R.id.ImgPersonatge)
        val lblDescripcioPersonatge = findViewById<TextView>(R.id.LblDescPersonatge)

        val imagePath = getFilesDir().toString() + "/IMG/" + personatges.get(pos!!).rutaPers
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imgPersonatgeImatge.setImageBitmap(bitmap)
        lblDescripcioPersonatge.text = personatges.get(pos!!).descripcioPers
    }

    private fun algoritmeSaberPersonatge(
        personatges: MutableList<Personatge>,
        encertsGenere: Array<Int>
    ): Int? {
        val respostesCorrectes = encertsGenere.sum()
        var i = 0
        var posicio: Int? = null
        var maxEncerts = 0

        if (respostesCorrectes == 0) {
            for (personatge in personatges) {
                if (personatge.nomPers.equals("Cap")) {
                    posicio = i
                }
                i++
            }
        } else if (respostesCorrectes == 20) {
            for (personatge in personatges) {
                if (personatge.nomPers.equals("Totes")) {
                    posicio = i
                }
                i++
            }
        } else {
            var posicionsMaximes = mutableListOf<Int>()
            for (encertGenere: Int in encertsGenere) {
                if (encertGenere > maxEncerts) {
                    maxEncerts = encertGenere
                }
            }
            for (encertGenere: Int in encertsGenere) {
                if (encertGenere == maxEncerts) {
                    posicionsMaximes.add(i)
                }
                i++
            }
            var genereMaxim = (Math.random() * (posicionsMaximes.size - 0)).toInt()
            val genere: String
            if (genereMaxim == 0) {
                genere = "Drama"
            } else if (genereMaxim == 1) {
                genere = "Terror"
            } else if (genereMaxim == 2) {
                genere = "Animació"
            } else if (genereMaxim == 3) {
                genere = "Ciencia ficció"
            } else {
                genere = "Acció"
            }
            val preguntasCorrectas = encertsGenere.get(genereMaxim)
            val percentatgeCorrecte = preguntasCorrectas / 4 * 100
            var iterator = 0
            //
            //
            //
            //MIRAR CONDICIONALES!

            //ENTRA A MÉS D'1

            //POSAR MENOR QUE 33 PERÒ SEGURNT MAJOR QUE 33 I MENOR QUE 66
            //
            //
            //
            //
            when (genere) {
                "Drama" -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Drama") && personatge.percEncerts < 33) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte >= 33 && percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Drama") && personatge.percEncerts < 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Drama") && personatge.percEncerts >= 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                "Terror" -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Terror") && personatge.percEncerts < 33) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                    else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Terror") && personatge.percEncerts < 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                    else {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Terror") && personatge.percEncerts >= 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                "Animació" -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Animació") && personatge.percEncerts < 33) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Animació") && personatge.percEncerts < 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Animació") && personatge.percEncerts >= 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }

                }
                "Ciencia ficció" -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Ciencia ficció") && personatge.percEncerts < 33) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Ciencia ficció") && personatge.percEncerts < 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Ciencia ficció") && personatge.percEncerts >= 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                "Acció" -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Acció") && personatge.percEncerts < 33) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Acció") && personatge.percEncerts < 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere.equals("Acció") && personatge.percEncerts >= 66) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
            }
        }
        return posicio
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
            //intent.putExtra(Keys.constKeys.RANKING_TO_FINAL, )
            startActivity(intent)
        }
    }
}