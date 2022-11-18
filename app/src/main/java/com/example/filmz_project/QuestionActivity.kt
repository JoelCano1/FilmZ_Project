package com.example.filmz_project

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import kotlin.random.Random

class QuestionActivity : AppCompatActivity() {
    private lateinit var timer: CountDownTimer

    companion object {
        var dramaCounter = 0;
        var terrorCounter = 0;
        var animationCounter = 0;
        var sfCounter = 0;
        var actionCounter = 0;

        var numQuestion = 1

        var yourCorrectQuestion = 0
        var correctAnswer = false
    }

    fun progressBar() {
        val timeBar = findViewById(R.id.ProgressBar) as ProgressBar
        val currentProgressBar = 1000
        timeBar.progress = 0
        timeBar.max = 1000

        ObjectAnimator.ofInt(timeBar, "progress", currentProgressBar)
            .setDuration(21500)
            .start()
    }

    fun timeQuestion() {

        var time = findViewById(R.id.timePreg) as TextView
        var seconds = 21


        timer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = seconds - 1
                time.text = seconds.toString()

            }

            override fun onFinish() {

            }
        }.start()
    }

    fun selectJson(usuarioActial: User): String {
        var language = resources.getConfiguration().locale.getLanguage()
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
        when (usuarioActial.difficult) {
            1 -> {
                difficulty = "FACIL"
            }
            2 -> {
                difficulty = "MEDIANO"
            }
            3 -> {
                difficulty = "DIFICIL"
            }
        }

        return "/JSONS/" + idiom + difficulty + ".json"
    }

    //carga el json de preguntas
    fun loadQuestions(usuarioActial: User): MutableList<Questions> {
        //getFilesDir() -> crea un objeto que hace referencia a la carpeta files donde se guardan los jsons
        val jsonFilePath = getFilesDir().toString() + selectJson(usuarioActial)
        val jsonFiles = FileReader(jsonFilePath)
        val questionsList = object : TypeToken<MutableList<Questions>>() {}.type
        val questions: MutableList<Questions> = Gson().fromJson(jsonFiles, questionsList)
        return questions
    }

    fun showQuestions(questionToShow: Questions) {


        val question = findViewById(R.id.question) as TextView
        val respuesta1 = findViewById(R.id.respuesta1) as Button
        val respuesta2 = findViewById(R.id.respuesta2) as Button
        val respuesta3 = findViewById(R.id.respuesta3) as Button
        val categoria = findViewById(R.id.categoria) as TextView

        val numPregunta = findViewById(R.id.numPreg) as TextView

        question.text = questionToShow.pregunta
        respuesta1.text = questionToShow.resposta1
        respuesta2.text = questionToShow.resposta2
        respuesta3.text = questionToShow.resposta3
        categoria.text = questionToShow.categoria
        numPregunta.text = numQuestion.toString()+"/20"


    }

    fun clasifyQuestions(questionShowed: Questions): Boolean {
        var fullQuestion = false
        when (questionShowed.categoria) {
            "Drama" -> {
                dramaCounter++
                if (dramaCounter > 4) {
                    fullQuestion = true
                }
            }
            "Terror" -> {
                terrorCounter++
                if (terrorCounter > 4) {
                    fullQuestion = true
                }
            }
            "Animación" -> {
                animationCounter++
                if (animationCounter > 4) {
                    fullQuestion = true
                }
            }
            "Ciencia Ficción" -> {
                sfCounter++
                if (sfCounter > 4) {
                    fullQuestion = true
                }
            }
            "Acción" -> {
                actionCounter++
                if (actionCounter > 4) {
                    fullQuestion = true
                }
            }

        }
        return fullQuestion
    }

    fun showRandomQuestion(questions: MutableList<Questions>) {
        var max = questions.size
        var random = 0
        do {

            random = Random.nextInt(0, max)

        } while (clasifyQuestions(questions[random]) && numQuestion < 20)
        //muestra la pregutnta
        showQuestions(questions[random])

        //iniciamos contador y barra
        progressBar()
        timeQuestion()

        //guardamos que respuesta es correcta
        checkCorrectAnswer(questions[random])
        //borra la pregunta para que no vuelve a salir i reduce el random pq se ha reducido la lista
        questions.removeAt(random)
        max--

    }
    fun checkCorrectAnswer(questionToCheck :Questions) {

        if (questionToCheck.resposta_correcte == yourCorrectQuestion)
        {
            correctAnswer = true
        }



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)


        val intent = getIntent()
        //var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User


        val jugadorActual = User("Juan", "123", 18,true, 'H', 148, true, 3, null)

        //cargamos el json una vez
        val loadedJSON = loadQuestions(jugadorActual)

        showRandomQuestion(loadedJSON)
        //botones
        val button1 = findViewById(R.id.respuesta1) as Button
        val button2 = findViewById(R.id.respuesta2) as Button
        val button3 = findViewById(R.id.respuesta3) as Button

        button1.setOnClickListener()
        {
            yourCorrectQuestion = 1
            button1.setBackgroundResource(R.drawable.boton_redondselct)
            button2.setBackgroundResource(R.drawable.boton_redondeado)
            button3.setBackgroundResource(R.drawable.boton_redondeado)

        }
        button2.setOnClickListener()
        {
            yourCorrectQuestion = 2
            button1.setBackgroundResource(R.drawable.boton_redondeado)
            button2.setBackgroundResource(R.drawable.boton_redondselct)
            button3.setBackgroundResource(R.drawable.boton_redondeado)
        }
        button3.setOnClickListener()
        {
            yourCorrectQuestion = 3
            button1.setBackgroundResource(R.drawable.boton_redondeado)
            button2.setBackgroundResource(R.drawable.boton_redondeado)
            button3.setBackgroundResource(R.drawable.boton_redondselct)
        }

        //validar
        val valideteQuestion = findViewById(R.id.validateQuestion) as ImageButton

        valideteQuestion.setOnClickListener()
        {
            valideteQuestion.visibility = View.INVISIBLE
            if (correctAnswer)
            {
                when (yourCorrectQuestion)
                {
                    1 -> {
                        button1.setBackgroundResource(R.drawable.boton_redondeadocrrct)
                    }
                    2 -> {
                        button2.setBackgroundResource(R.drawable.boton_redondeadocrrct)
                    }
                    3 -> {
                        button3.setBackgroundResource(R.drawable.boton_redondeadocrrct)
                    }
                }
            }
        }

        //nextquestion
        val nextQuestion = findViewById(R.id.nextQuestion) as ImageButton
        nextQuestion.setOnClickListener()
        {
            valideteQuestion.visibility = View.VISIBLE
            correctAnswer = false
            timer.cancel()
            numQuestion++

            if (numQuestion > 20) {
                val intent = Intent(this, ResultActivity::class.java)
                //intent.putExtra(Keys.constKeys.DIFFICULT_TO_QUIZ, user)
                startActivity(intent)
            } else {
                showRandomQuestion(loadedJSON)
            }

        }
    }
}