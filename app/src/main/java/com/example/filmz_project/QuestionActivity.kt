package com.example.filmz_project

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class QuestionActivity : AppCompatActivity() {

    fun  progressBar(){
        val timeBar = findViewById(R.id.ProgressBar) as ProgressBar
        val currentProgressBar = 1000
        timeBar.max = 1000

        ObjectAnimator.ofInt(timeBar, "progress", currentProgressBar)
            .setDuration(21500)
            .start()
    }

    fun timeQuestion(){

        val time = findViewById(R.id.timePreg) as TextView
        var seconds = 21

        object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = seconds - 1
                time.text = seconds.toString()

            }

            override fun onFinish() {

            }
        }.start()
    }

    fun selectJson(usuarioActial:User): String
    {   var language = resources.getConfiguration().locale.getLanguage()
        var idiom = "x"
        var difficulty = "x"

        when (language) {
            "ca" -> {
                idiom = "CATALA_"
            }
            "es" -> {
                idiom = "CASTELLANO_"
            }
            "en" -> {
                idiom = "ENGLISH_"
            }
        }
        when (usuarioActial.difficult)
        {
            1 -> {
                difficulty = "FACIL"
            }
            2 -> {
                difficulty = "MEDIANO"
            }
            3 -> {
                difficulty="DIFICIL"
            }
        }

        return "/JSONS/" + idiom + difficulty + ".json"
    }

    //carga el json de preguntas
    fun loadQuestions(usuarioActial:User) :MutableList<Questions>
    {
        //getFilesDir() -> crea un objeto que hace referencia a la carpeta files donde se guardan los jsons
        val jsonFilePath = getFilesDir().toString() + selectJson(usuarioActial)
        val jsonFiles = FileReader(jsonFilePath)
        val questionsList = object: TypeToken<MutableList<Questions>>() {}.type
        val questions: MutableList<Questions> = Gson().fromJson(jsonFiles, questionsList)
        return questions
    }
    fun showQuestions(questions : MutableList<Questions>){

        var numero = 0
        var max = questions.size

        while (numero > 4)
        {
            var random = (0..max).random()

            val question = findViewById(R.id.question) as TextView
            val respuesta1 = findViewById(R.id.respuesta1) as Button
            val respuesta2 = findViewById(R.id.respuesta2) as Button
            val respuesta3 = findViewById(R.id.respuesta3) as Button
            val nombrePeli = findViewById(R.id.nombrePeli) as TextView
            val categoria = findViewById(R.id.categoria) as TextView

            question.text = questions[random].pregunta
            respuesta1.text = questions[random].resposta1
            respuesta2.text = questions[random].resposta2
            respuesta3.text = questions[random].resposta3
            nombrePeli.text = questions[random].película
            categoria.text = questions[random].categoria
            questions.removeAt(random)
            max--
            numero++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)

        val intent = getIntent()
        //var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User

        //Progres bar
        progressBar()

        //temporizador
        timeQuestion()

        val jugadorActual = User("Juan", "123", 18,true, 'H', 148, true, 3, null)




        loadQuestions(jugadorActual)

        val question = findViewById(R.id.question) as TextView
        val respuesta1 = findViewById(R.id.respuesta1) as Button
        val respuesta2 = findViewById(R.id.respuesta2) as Button
        val respuesta3 = findViewById(R.id.respuesta3) as Button
        val nombrePeli = findViewById(R.id.nombrePeli) as TextView
        val categoria = findViewById(R.id.categoria) as TextView
        question.text = loadQuestions(jugadorActual)[0].pregunta
        respuesta1.text = loadQuestions(jugadorActual)[0].resposta1
        respuesta2.text = loadQuestions(jugadorActual)[0].resposta2
        respuesta3.text = loadQuestions(jugadorActual)[0].resposta3
        nombrePeli.text = loadQuestions(jugadorActual)[0].película
        categoria.text = loadQuestions(jugadorActual)[0].categoria
    }
}