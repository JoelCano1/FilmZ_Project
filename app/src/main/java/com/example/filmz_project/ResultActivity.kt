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
        val encertsGenere = intent.getSerializableExtra(Keys.constKeys.QUESTIONS_TO_RESULT2) as Array<Int>

        //val jugadorActual = User("Juan", "123", 18, 'H', 5, true, 2, null)
        //val encertsGenere = arrayOf(0, 0, 0, 0, 3)

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
        veureRanking(jugadorActual)
        continuar(jugadorActual)
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
        posarEncertsTotals(encertsGenere, jugadorActual)

        //POSAR ENCERTS PER GENERE
        posarEncertsGenere(encertsGenere)
    }

    private fun posarNom(jugadorActual: User) {
        val lblNomUser = findViewById<TextView>(R.id.LblNomUser)
        lblNomUser.text = jugadorActual.nom
    }

    private fun posarEncertsTotals(encertsGenere: Array<Int>, jugadorActual: User) {
        val lblEncertsTotals = findViewById<TextView>(R.id.LblEncertsTotal)
        val sumEncerts = encertsGenere.sum();
        jugadorActual.puntuacio = sumEncerts
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

        val imagePath = getFilesDir().toString() + "/IMG/" + personatges[pos!!].rutaPers
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imgPersonatgeImatge.setImageBitmap(bitmap)
        lblDescripcioPersonatge.text = personatges[pos!!].descripcioPers
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
                if (personatge.nomPers == "Cap") {
                    posicio = i
                }
                i++
            }
        } else if (respostesCorrectes == 20) {
            for (personatge in personatges) {
                if (personatge.nomPers == "Totes") {
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
            val GENERE_DRAMA = "Drama"; val GENERE_TERROR = "Terror"; val GENERE_ANIMACIO = "Animació"; val GENERE_CIENCIA_FICCIO = "Ciencia ficció"; val GENERE_ACCIO = "Acció"
            if (posicionsMaximes[genereMaxim] == 0) {
                genere = GENERE_DRAMA
            } else if (posicionsMaximes[genereMaxim] == 1) {
                genere = GENERE_TERROR
            } else if (posicionsMaximes[genereMaxim] == 2) {
                genere = GENERE_ANIMACIO
            } else if (posicionsMaximes[genereMaxim] == 3) {
                genere = GENERE_ACCIO
            } else {
                genere = GENERE_CIENCIA_FICCIO
            }
            val idGenereSeleccionat = posicionsMaximes[genereMaxim]
            val preguntasCorrectas = encertsGenere[idGenereSeleccionat]
            val percentatgeCorrecte = preguntasCorrectas * 100 / 4
            var iterator = 0

            when (genere) {
                GENERE_DRAMA -> {
                    if (percentatgeCorrecte < 33) {
                        //Fer un while perque un cop trobat s'aturi
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_DRAMA) && personatge.percEncerts == 33f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_DRAMA) && personatge.percEncerts == 66f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_DRAMA) && personatge.percEncerts == 100f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                GENERE_TERROR -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_TERROR) && personatge.percEncerts == 33f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                    else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_TERROR) && personatge.percEncerts == 66f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                    else {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_TERROR) && personatge.percEncerts == 100f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                GENERE_ANIMACIO -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ANIMACIO) && personatge.percEncerts == 33f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ANIMACIO) && personatge.percEncerts == 66f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ANIMACIO) && personatge.percEncerts == 100f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }

                }
                GENERE_CIENCIA_FICCIO -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_CIENCIA_FICCIO) && personatge.percEncerts == 33f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_CIENCIA_FICCIO) && personatge.percEncerts == 66f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_CIENCIA_FICCIO) && personatge.percEncerts == 100f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    }
                }
                GENERE_ACCIO -> {
                    if (percentatgeCorrecte < 33) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ACCIO) && personatge.percEncerts == 33f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else if (percentatgeCorrecte < 66) {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ACCIO) && personatge.percEncerts == 66f) {
                                posicio = iterator
                            }
                            iterator++
                        }
                    } else {
                        for (personatge in personatges) {
                            if (personatge.genere == (GENERE_ACCIO) && personatge.percEncerts == 100f) {
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

    private fun veureRanking(jugadorActual: User) {
        val btnRanking = findViewById<Button>(R.id.BtnRankingResult)

        btnRanking.setOnClickListener() {
            val intent = Intent(this, RankingActivity::class.java)
            intent.putExtra(Keys.constKeys.RESULT_TO_RANKING, jugadorActual)
            startActivity(intent)
        }
    }

    private fun continuar(jugadorActual: User) {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarResult)

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, FinalActivity::class.java)
            intent.putExtra(Keys.constKeys.RESULT_TO_FINAL, jugadorActual)
            startActivity(intent)
        }
    }
}