package com.example.filmz_project

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
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
    fun selectJson(): String
    {
        return "s"
    }

    //carga el json de preguntas
    fun loadQuestions() :MutableList<Questions>
    {
        //getFilesDir() -> crea un objeto que hace referencia a la carpeta files donde se guardan los jsons
        val jsonFilePath = getFilesDir().toString() +"/JSONS/CASTELLANO_DIFICIL.json"
        val jsonFiles = FileReader(jsonFilePath)
        val questionsList = object: TypeToken<MutableList<Questions>>() {}.type
        val questions: MutableList<Questions> = Gson().fromJson(jsonFiles, questionsList)
        return questions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)


        //Progres bar
        progressBar()

        //temporizador
        timeQuestion()

        val question = findViewById(R.id.question) as TextView
        question.text = loadQuestions()[0].pregunta

    }
}