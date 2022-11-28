package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class LoginActivity : AppCompatActivity() {

    /**
     * @author Albert
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        //Declarem totes les variables de les views que es faran servir a aquesta activity
        val txtNomRegistre = findViewById(R.id.TxtNomRegistre) as EditText
        val txtContraRegistre = findViewById(R.id.TxtContraRegistre) as EditText
        val btnMostrarContraRegistre = findViewById(R.id.BtnMostrarContraRegistre) as ImageButton
        val btnIniciarSessio = findViewById(R.id.BtnIniciarSessio) as Button
        val btnObrirRegistre = findViewById(R.id.BtnObrirRegistre) as Button

        //Rebem la llista d'usuaris existens al Json on es guarden aquests

        val usersList = getUsers()

        //Controlem si es mostra la contrassenya o no quan es prem l'imageButton MostrarContraRegistre
        var show = true;

        //Controlem si es mostra la contrassenya o no
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

        /*En cas que el nom d'usuari i la contrasenya coincideixin anem a la següent activity indicant que es el jugador actual
        o en cas contrari mostrem un Toast amb un missatge d'error*/
        btnIniciarSessio.setOnClickListener() {

            val x = userExists(
                usersList, txtNomRegistre.text.toString(), txtContraRegistre.text.toString()
            )

            if (x >= 0) {

                val user = usersList[x]
                user.jugadorActual = true;

                val intent = Intent(this, DifficultActivity::class.java)

                intent.putExtra(Keys.constKeys.LOGIN_TO_DIFFICULTY, user)

                startActivity(intent)

            } else {

                Toast.makeText(
                    applicationContext,
                    resources.getText(R.string.login_screen_toast_error),
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

        //En cas que es vulgui crear un usuari obrim l'activity registre
        btnObrirRegistre.setOnClickListener() {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

    }

    //Carreguem el Json Usuaris a una llista i la retornem
    fun getUsers(): MutableList<User> {

        val jsonFilePath = getFilesDir().toString() + "/JSONS/USUARIS_android.json"
        val jsonFile = FileReader(jsonFilePath)
        val listUsersType = object : TypeToken<MutableList<User>>() {}.type
        val users: MutableList<User> = Gson().fromJson(jsonFile, listUsersType)

        return users
    }

    //Confirmem que l'usuari existeix a la llista i retornem la posició d'aquest
    fun userExists(list: MutableList<User>, userName: String, password: String): Int {

        var toReturn = -1
        var cont = true
        var i = 0

        do {

            if (userName == list[i].nom && password == list[i].contrasenya) {

                toReturn = i;
                cont = false

            }

            i++

        } while (i < list.count() && cont)

        return toReturn

    }
}