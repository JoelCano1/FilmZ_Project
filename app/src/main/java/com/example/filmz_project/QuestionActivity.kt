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
    private lateinit var progressBar: ProgressBar

    companion object {
        var dramaCounter = 0
        var terrorCounter = 0
        var animationCounter = 0
        var sfCounter = 0
        var actionCounter = 0

        var numQuestion = 1

        var yourCorrectQuestion = -1
        var correctAnswer = 0

        var dramaCorrect = 0; var terrorCorrect = 0; var animationCorrect = 0; var sfCorrect = 0; var actionCorrect = 0;
    }

    fun progressBar() {
        val timeBar = findViewById(R.id.ProgressBar) as ProgressBar
        progressBar = timeBar
        val currentProgressBar = 1000
        timeBar.progress = 0
        timeBar.max = 1000

        ObjectAnimator.ofInt(timeBar, "progress", currentProgressBar)
            .setDuration(21500)
            .start()
    }

    fun timeQuestion(button1 : Button, button2 : Button, button3 : Button, valideteQuestion : ImageButton, currentQuestion :Questions) {

        var time = findViewById(R.id.timePreg) as TextView
        var seconds = 21


        timer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = seconds - 1
                time.text = seconds.toString()

            }

            override fun onFinish() {
                validateQuestion(button1 , button2 , button3 , valideteQuestion, currentQuestion)
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

    fun showRandomQuestion(questions: MutableList<Questions>, button1 : Button, button2 : Button, button3 : Button, valideteQuestion : ImageButton) :Int{
        var max = questions.size
        var random = 0
        do {

            //random = Random.nextInt(0, max)
            random = (Math.random()* (max-0)).toInt()

        } while (clasifyQuestions(questions[random]) && numQuestion < 20)
        //muestra la pregutnta
        showQuestions(questions[random])

        //iniciamos contador y barra
        progressBar()
        timeQuestion(button1 , button2 , button3 , valideteQuestion, questions[random])

        //guardamos que respuesta es correcta
        setCorrectAnswer(questions[random])

        //borra la pregunta para que no vuelve a salir i reduce el random pq se ha reducido la lista
        questions.removeAt(random)
        max--

        return random
    }
    fun setCorrectAnswer(questionToCheck :Questions) {

        correctAnswer = questionToCheck.resposta_correcte
    }

    fun validateQuestion(button1 : Button, button2 : Button, button3 : Button, valideteQuestion : ImageButton, currentQuestion :Questions)
    {
        valideteQuestion.visibility = View.INVISIBLE
        if (yourCorrectQuestion == correctAnswer)
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
            addCorrectCategory(currentQuestion)
        }else {
            when (yourCorrectQuestion)
            {
                1 -> {
                    button1.setBackgroundResource(R.drawable.boton_redondeadoincrrct)
                }
                2 -> {
                    button2.setBackgroundResource(R.drawable.boton_redondeadoincrrct)
                }
                3 -> {
                    button3.setBackgroundResource(R.drawable.boton_redondeadoincrrct)
                }
            }

            if (yourCorrectQuestion == -1)
            {
                when (correctAnswer)
                {
                    1 -> {
                        button1.setBackgroundResource(R.drawable.boton_redondeadoorange)
                    }
                    2 -> {
                        button2.setBackgroundResource(R.drawable.boton_redondeadoorange)
                    }
                    3 -> {
                        button3.setBackgroundResource(R.drawable.boton_redondeadoorange)
                    }
                }

            }else {
                when (correctAnswer)
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

    }
    fun addCorrectCategory (currentQuestion: Questions)
    {
        when (currentQuestion.categoria)
        {
            "Drama" -> {
                dramaCorrect++
            }
            "Terror" -> {
                terrorCorrect++
            }
            "Animación" -> {
                animationCorrect++
            }
            "Ciencia Ficción" -> {
                sfCorrect++
            }
            "Acción" -> {
                actionCorrect++
            }
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)



        //boton validar, botones de respuesta y boton de siguiente pregunta
        val valideteQuestion = findViewById(R.id.validateQuestion) as ImageButton
        val button1 = findViewById(R.id.respuesta1) as Button
        val button2 = findViewById(R.id.respuesta2) as Button
        val button3 = findViewById(R.id.respuesta3) as Button
        val nextQuestion = findViewById(R.id.nextQuestion) as ImageButton
        val timeLabel = findViewById(R.id.timePreg) as TextView

        val intent = getIntent()
        //var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User
        val jugadorActual = User("Juan", "123", 18,true, 'H', 148, true, 2, null)

        //cargamos el json una vez
        val loadedJSON = loadQuestions(jugadorActual)

        //guardamso la posicion de la pregunta que enseñamos por pantalla
        var currentQuestion = showRandomQuestion(loadedJSON, button1 , button2 , button3 , valideteQuestion)


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


        //validamos si la pregunta esta bien validada
        valideteQuestion.setOnClickListener()
        {
            validateQuestion(button1 , button2 , button3 , valideteQuestion, loadedJSON[currentQuestion] )
            timer.cancel()
            progressBar.visibility = View.INVISIBLE
            timeLabel.visibility = View.INVISIBLE

        }

        //pasamos de pregunta
        nextQuestion.setOnClickListener()
        {
            //reseteamos valores
            valideteQuestion.visibility = View.VISIBLE
            button1.setBackgroundResource(R.drawable.boton_redondeado)
            button2.setBackgroundResource(R.drawable.boton_redondeado)
            button3.setBackgroundResource(R.drawable.boton_redondeado)
            timer.cancel()
            progressBar.visibility = View.VISIBLE
            timeLabel.visibility = View.VISIBLE
            yourCorrectQuestion = -1

            //sumamos una pregunta
            numQuestion++

            if (numQuestion > 20) {
                val correctCategory = arrayOf(dramaCorrect, terrorCorrect, animationCorrect, sfCorrect, actionCorrect)

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT, jugadorActual)
                intent.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT2, correctCategory)
                startActivity(intent)
            } else {
                showRandomQuestion(loadedJSON, button1 , button2 , button3 , valideteQuestion)
            }

        }
    }
}