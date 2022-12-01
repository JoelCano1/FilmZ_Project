package com.example.filmz_project

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DifficultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.difficult_screen)

        val intentLogin = getIntent()

        var user = intentLogin.getSerializableExtra(Keys.constKeys.TO_DIFFICULT) as User
        var musicPos = intentLogin.getSerializableExtra(Keys.constKeys.AUDIO_LOGIN) as Int
        //Posar audio
        val mediaPlayerDifficultActivity = MediaPlayer.create(this,R.raw.musicmenu)
        mediaPlayerDifficultActivity.seekTo(musicPos)
        mediaPlayerDifficultActivity.start()
        mediaPlayerDifficultActivity.setLooping(true)

        seleccioNivell(user, mediaPlayerDifficultActivity)
    }
    private fun seleccioNivell(user:User, mediaPlayerDifficultActivity: MediaPlayer) {
        val SPEED_EASY = 0.7f
        val SPEED_MEDIUM = 1f
        val SPEED_HARD = 1.3f

        val btnFacil = findViewById<Button>(R.id.BtnEasy)
        val btnMitja = findViewById<Button>(R.id.BtnMedium)
        val btnDificil = findViewById<Button>(R.id.BtnDificult)
        val lblDescripcioDificultat = findViewById<TextView>(R.id.LblDescDif)

        btnFacil.setOnClickListener() {
            mediaPlayerDifficultActivity.setPlaybackParams(mediaPlayerDifficultActivity.getPlaybackParams().setSpeed(SPEED_EASY))
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatFacil)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnFacil.backgroundTintList = this.getColorStateList(R.color.verd)
            user.difficult = 1
            continuar(user, mediaPlayerDifficultActivity)
        }
        btnMitja.setOnClickListener() {
            mediaPlayerDifficultActivity.setPlaybackParams(mediaPlayerDifficultActivity.getPlaybackParams().setSpeed(SPEED_MEDIUM))
            lblDescripcioDificultat.text = resources.getText(R.string.difficult_screen_textDificultatMitja)
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnMitja.backgroundTintList = this.getColorStateList(R.color.taronja)
            user.difficult = 2
            continuar(user, mediaPlayerDifficultActivity)
        }
        btnDificil.setOnClickListener() {
            mediaPlayerDifficultActivity.setPlaybackParams(mediaPlayerDifficultActivity.getPlaybackParams().setSpeed(SPEED_HARD))
            lblDescripcioDificultat.text = resources.getText((R.string.difficult_screen_textDificultatDificil))
            oscurecerButtons(btnFacil, btnMitja, btnDificil)
            btnDificil.backgroundTintList = this.getColorStateList(R.color.vermell)
            user.difficult = 3
            continuar(user, mediaPlayerDifficultActivity)
        }
        continuar(user, mediaPlayerDifficultActivity)
    }
    private fun continuar(user: User, mediaPlayerDifficultActivity: MediaPlayer) {
        val btnContinuar = findViewById<Button>(R.id.BtnContinuarDificultat)

        btnContinuar.setOnClickListener() {
            val intent = Intent(this, DiffToQuest::class.java)
            intent.putExtra(Keys.constKeys.DIFFICULT_TO_QUIZ, user)
            startActivity(intent)
            mediaPlayerDifficultActivity.stop()
        }
    }
    private fun oscurecerButtons(btnFacil: Button, btnMitja: Button, btnDificil: Button) {
        btnFacil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnMitja.backgroundTintList = this.getColorStateList(R.color.gris_clar)
        btnDificil.backgroundTintList = this.getColorStateList(R.color.gris_clar)
    }
}