package com.example.filmz_project

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        //audio play
        val mediaPlayerLogin = MediaPlayer.create(this, R.raw.musicmenu)
        mediaPlayerLogin.start()
        mediaPlayerLogin.setLooping(true)

        val txtNomRegistre = findViewById(R.id.TxtNomRegistre) as EditText
        val txtContraRegistre = findViewById(R.id.TxtContraRegistre) as EditText
        val btnMostrarContraRegistre = findViewById(R.id.BtnMostrarContraRegistre) as ImageButton
        val btnIniciarSessio = findViewById(R.id.BtnIniciarSessio) as Button
        val btnObrirRegistre = findViewById(R.id.BtnObrirRegistre) as Button

        txtNomRegistre.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })
        txtContraRegistre.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        })

        val usersList = getUsers()


        var show = true;
        btnMostrarContraRegistre.setOnClickListener() {

            if (show) {
                txtContraRegistre.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                show = false
            } else {
                txtContraRegistre.transformationMethod = PasswordTransformationMethod.getInstance()
                show = true
            }

        }

        btnIniciarSessio.setOnClickListener() {

            val x = userExists(
                usersList, txtNomRegistre.text.toString(), txtContraRegistre.text.toString()
            )

            if (x >= 0) {

                val user = usersList[x]
                user.jugadorActual = true;

                //Stop audio
                mediaPlayerLogin.stop()
                var musicPos = mediaPlayerLogin.currentPosition
                val intent = Intent(this, DifficultActivity::class.java)
                intent.putExtra(Keys.constKeys.TO_DIFFICULT, user)
                intent.putExtra(Keys.constKeys.AUDIO_LOGIN, musicPos)
                startActivity(intent)
            } else {

                Toast.makeText(
                    applicationContext,
                    resources.getText(R.string.login_screen_toast_error),
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

        btnObrirRegistre.setOnClickListener() {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    private fun getUsers(): MutableList<User> {

        val jsonFilePath = getFilesDir().toString() + "/JSONS/USUARIS_android.json"
        val jsonFile = FileReader(jsonFilePath)
        val listUsersType = object : TypeToken<MutableList<User>>() {}.type
        val users: MutableList<User> = Gson().fromJson(jsonFile, listUsersType)

        return users
    }

    private fun userExists(list: MutableList<User>, userName: String, password: String): Int {

        var toReturn = -1
        var cont = true
        var i = 0

        do {

            if (userName == list[i].nom && Blowfish.checkPassword(list[i].contrasenya,password)) {

                toReturn = i;
                cont = false

            }

            i++

        } while (i < list.count() && cont)

        return toReturn

    }
    //desactivamos la funcion de volver
    override fun onBackPressed() {
        //super.onBackPressed()
    }
}