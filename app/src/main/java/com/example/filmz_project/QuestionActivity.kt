package com.example.filmz_project

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.claucookie.miniequalizerlibrary.EqualizerView
import java.io.FileReader
import java.util.*
import kotlin.concurrent.schedule

class QuestionActivity : AppCompatActivity() {
    private lateinit var timer: CountDownTimer
    private lateinit var progressBar: ProgressBar

    companion object {
        // contadores de preguntas mostradas por categoria
        var dramaCounter = 0;
        var terrorCounter = 0;
        var animationCounter = 0;
        var sfCounter = 0;
        var actionCounter = 0;

        //en que pregunta estamos y el valor de esta pregunta
        var numQuestion = 1
        var currentQuestion: Questions? = null

        //respuesta del usuario y respuesta correcta de la pregunta
        var yourCorrectQuestion = -1
        var correctAnswer = 0

        //contadores de categorias acertadas
        var dramaCorrect = 0;
        var terrorCorrect = 0;
        var animationCorrect = 0;
        var sfCorrect = 0;
        var actionCorrect = 0;
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

    fun timeQuestion(
        button1: Button,
        button2: Button,
        button3: Button,
        animationView: LottieAnimationView
    ) {

        var time = findViewById(R.id.timePreg) as TextView
        var seconds = 21


        timer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = seconds - 1
                time.text = seconds.toString()

            }

            override fun onFinish() {
                validateQuestion(button1, button2, button3, animationView)
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
        numPregunta.text = (numQuestion).toString() + "/20"

    }

    fun clasifyQuestions(questionShowed: Questions): Boolean {

        var fullQuestion = false
        when (questionShowed.categoria) {
            "Drama" -> {

                if (dramaCounter == 4) {
                    fullQuestion = true
                }else{
                    dramaCounter++
                }
            }
            "Terror" -> {

                if (terrorCounter == 4) {
                    fullQuestion = true
                }else{
                    terrorCounter++
                }
            }
            "Animaci??n" -> {

                if (animationCounter == 4) {
                    fullQuestion = true
                }else{
                    animationCounter++
                }
            }
            "Ciencia Ficci??n" -> {

                if (sfCounter == 4) {
                    fullQuestion = true
                }else{
                    sfCounter++
                }
            }
            "Acci??n" -> {

                if (actionCounter == 4) {
                    fullQuestion = true
                }else{
                    actionCounter++
                }
            }

        }
        return fullQuestion
    }

    fun showRandomQuestion(
        questions: MutableList<Questions>,
        button1: Button,
        button2: Button,
        button3: Button,
        animationView: LottieAnimationView,
        mediaPlayerPregunta: MediaPlayer,
        equalizerView: EqualizerView
    ) {
        var max = questions.size
        var random = 0
        do {

            //random = Random.nextInt(0, max)
            random = (Math.random() * (max - 0)).toInt()

        } while (clasifyQuestions(questions[random]) && numQuestion < 21)
        //muestra la pregutnta
        showQuestions(questions[random])

        //cogemos la extension de la ruta de img o audio
        var extension = questions[random].imgaudio.takeLast(4)
        val EXTENSION_IMAGE = ".png"
        val EXTENSION_AUDIO = ".mp3"

        //ponemos la imagen de la pregunta en el caso de que la extension sea .png
        if (extension == EXTENSION_IMAGE) {
            val imatgePregunta = findViewById(R.id.ImgPregunta) as ImageView
            val contingutPregunta = findViewById(R.id.LblTextPregunta) as TextView
            val linearImatge = findViewById(R.id.LinearImatge) as LinearLayout
            val linearPregunta = findViewById(R.id.LinearPregunta) as LinearLayout

            //muestra la imagen
            mostrarImatge(imatgePregunta, contingutPregunta, questions[random], linearImatge, linearPregunta)

            //en el caso que se haga un long click en la imagen se mostrar?? la pregunta
            linearImatge.setOnLongClickListener() {
                linearImatge.visibility = View.INVISIBLE
                linearPregunta.visibility = View.VISIBLE
                //iniciamos contador y barra
                progressBar()
                timeQuestion(button1, button2, button3, animationView)
                true
            }
        //ponemos el audio de la pregunta en el caso de que la extensi??n sea .mp3
        } else if (extension == EXTENSION_AUDIO) {
            posarAudioPregunta(questions[random], mediaPlayerPregunta, equalizerView)
            //iniciamos contador y barra
            progressBar()
            timeQuestion(button1, button2, button3, animationView)
        }
        else {
            //iniciamos contador y barra
            progressBar()
            timeQuestion(button1, button2, button3, animationView)
        }

        //guardamos que respuesta es correcta
        setCorrectAnswer(questions[random])

        currentQuestion = questions[random]

        //borra la pregunta para que no vuelve a salir i reduce el random pq se ha reducido la lista
        questions.removeAt(random)
        max--
    }

    private fun mostrarImatge(
        imatgePregunta: ImageView,
        contingutPregunta: TextView,
        questionShowed: Questions,
        linearImatge: LinearLayout,
        linearPregunta: LinearLayout
    ) {
        linearImatge.visibility = View.VISIBLE
        linearPregunta.visibility = View.INVISIBLE

        //Posar pregunta
        contingutPregunta.text = questionShowed.pregunta
        //Posar imatge
        val imagePath = getFilesDir().toString() + "/IMG/" + questionShowed.imgaudio
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imatgePregunta.setImageBitmap(bitmap)
    }

    private fun posarAudioPregunta(questionShowed: Questions, mediaPlayerPregunta: MediaPlayer, equalizerView: EqualizerView) {
        var audioPath = getFilesDir().toString() + "/AUDIO/" + questionShowed.imgaudio
        mediaPlayerPregunta.setDataSource(audioPath)
        mediaPlayerPregunta.prepare()
        mediaPlayerPregunta.start()
        mediaPlayerPregunta.setLooping(true)
        //equalizer view activar
        equalizerView.visibility = View.VISIBLE
        equalizerView.animateBars()
    }

    fun initializeVariables(){
        // contadores de preguntas mostradas por categoria
        dramaCounter = 0;
        terrorCounter = 0;
        animationCounter = 0;
        sfCounter = 0;
        actionCounter = 0;

        //en que pregunta estamos y el valor de esta pregunta
        numQuestion = 1


        //respuesta del usuario y respuesta correcta de la pregunta
         yourCorrectQuestion = -1
         correctAnswer = 0

        //contadores de categorias acertadas
        dramaCorrect = 0;
        terrorCorrect = 0;
        animationCorrect = 0;
        sfCorrect = 0;
        actionCorrect = 0;
    }
    fun setCorrectAnswer(questionToCheck: Questions) {

        correctAnswer = questionToCheck.resposta_correcte
    }

    fun setVisbility() {
        val timeLabel = findViewById(R.id.timePreg) as TextView
        val valideteQuestion = findViewById(R.id.validateQuestion) as ImageButton

        progressBar.visibility = View.INVISIBLE
        timeLabel.visibility = View.INVISIBLE
        valideteQuestion.visibility = View.INVISIBLE
    }

    fun setClickable(button1: Button, button2: Button, button3: Button) {
        button1.isClickable = false
        button2.isClickable = false
        button3.isClickable = false
    }
    fun setClickableNext(clickable: Boolean){
        val nextQuestion = findViewById(R.id.nextQuestion) as ImageButton
        nextQuestion.isClickable = clickable
    }

    fun validateQuestion(
        button1: Button,
        button2: Button,
        button3: Button,
        animationView: LottieAnimationView
    ) {
        setVisbility()
        setClickable(button1, button2, button3)
        setClickableNext(false)

        if (yourCorrectQuestion == correctAnswer) {
            animationView.visibility = View.VISIBLE
            validateAnimation(animationView, R.raw.correct)
            var mediaPlayerCorrecte = MediaPlayer.create(this,R.raw.correcte)
            addCorrectCategory()
            Timer("SettingUp", false).schedule(1010) {
                mediaPlayerCorrecte.start()
                when (yourCorrectQuestion) {
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
                setClickableNext(true)
            }
        } else {
            //animacion incorrecto
            animationView.visibility = View.VISIBLE
            validateAnimation(animationView, R.raw.wrong)
            var mediaPlayerIncorrecte = MediaPlayer.create(this,R.raw.incorrecte)

            Timer("SettingUp", false).schedule(1010) {
                mediaPlayerIncorrecte.start()
                when (yourCorrectQuestion) {
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
                setClickableNext(true)
            }


            if (yourCorrectQuestion == -1) {
                when (correctAnswer) {
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

            } else {
                Timer("SettingUp", false).schedule(1010) {
                    when (correctAnswer) {
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
                    setClickableNext(true)
                }
            }
        }

    }

    fun addCorrectCategory() {
        when (currentQuestion?.categoria) {
            "Drama" -> {
                dramaCorrect++
            }
            "Terror" -> {
                terrorCorrect++
            }
            "Animaci??n" -> {
                animationCorrect++
            }
            "Ciencia Ficci??n" -> {
                sfCorrect++
            }
            "Acci??n" -> {
                actionCorrect++
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)

        initializeVariables()

        //boton validar, botones de respuesta y boton de siguiente pregunta
        val valideteQuestion = findViewById(R.id.validateQuestion) as ImageButton
        val button1 = findViewById(R.id.respuesta1) as Button
        val button2 = findViewById(R.id.respuesta2) as Button
        val button3 = findViewById(R.id.respuesta3) as Button
        val nextQuestion = findViewById(R.id.nextQuestion) as ImageButton
        val timeLabel = findViewById(R.id.timePreg) as TextView

        //animaci??n vista
        val animationView = findViewById(R.id.animationShow) as LottieAnimationView

        val intent = getIntent()
        var jugadorActual = intent.getSerializableExtra(Keys.constKeys.DIFFICULT_TO_QUIZ) as User
        //val jugadorActual = User("Juan", "123", 18, 'H', 0, true, 2, null)

        //cargamos el json una vez
        val loadedJSON = loadQuestions(jugadorActual)

        var mediaPlayerPregunta = MediaPlayer()
        val equalizerView = findViewById(R.id.equalizer_view) as EqualizerView
        showRandomQuestion(loadedJSON, button1, button2, button3, animationView, mediaPlayerPregunta, equalizerView)


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
            setClickableNext(false)
            validateQuestion(button1, button2, button3, animationView)
            timer.cancel()
        }

        //pasamos de pregunta
        nextQuestion.setOnClickListener()
        {
            //Parar can??o quan la pregunta sigui resposta
            if (mediaPlayerPregunta != null) {
                mediaPlayerPregunta.setLooping(false)
                mediaPlayerPregunta.stop()
                mediaPlayerPregunta = MediaPlayer()
                equalizerView.stopBars()
                equalizerView.visibility = View.INVISIBLE
            }
            //reseteamos valores
            button1.setBackgroundResource(R.drawable.boton_redondeado)
            button2.setBackgroundResource(R.drawable.boton_redondeado)
            button3.setBackgroundResource(R.drawable.boton_redondeado)
            timer.cancel()

            button1.isClickable = true
            button2.isClickable = true
            button3.isClickable = true

            valideteQuestion.visibility = View.VISIBLE
            animationView.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            timeLabel.visibility = View.VISIBLE
            yourCorrectQuestion = -1

            //sumamos una pregunta
            numQuestion++

            if (numQuestion > 20) {
                val correctCategory = arrayOf(dramaCorrect, terrorCorrect, animationCorrect, sfCorrect, actionCorrect)
                val intent2 = Intent(this, QuestioToResult::class.java)
                intent2.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT, jugadorActual)
                intent2.putExtra(Keys.constKeys.QUESTIONS_TO_RESULT2, correctCategory)
                startActivity(intent2)

            } else {
                showRandomQuestion(loadedJSON, button1, button2, button3, animationView, mediaPlayerPregunta, equalizerView)
            }

        }
    }

    //desactivamos la funcion de volver
    override fun onBackPressed() {
        //super.onBackPressed()
    }
    private fun validateAnimation(imageView: LottieAnimationView, animation: Int) {
        imageView.setAnimation(animation)
        imageView.playAnimation()

    }
}