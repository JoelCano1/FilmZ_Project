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

    object constRegister {

        const val NOMUSER = "NOM"
        const val CONTRAUSER = "Contra"

    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    )
    {
        val txtNomRegitre = findViewById(R.id.TxtNomRegistre) as EditText
        if (it.resultCode == RESULT_OK) {

            val name = it.data?.getStringExtra(constRegister.NOMUSER)
            //txtNomRegitre.text = name;

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        val txtNomRegistre = findViewById(R.id.TxtNomRegistre) as EditText
        val txtContraRegistre = findViewById(R.id.TxtContraRegistre) as EditText
        val btnMostrarContraRegistre = findViewById(R.id.BtnMostrarContraRegistre) as ImageButton
        val btnIniciarSessio = findViewById(R.id.BtnIniciarSessio) as Button
        val btnObrirRegistre = findViewById(R.id.BtnObrirRegistre) as Button

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

            /*val x = userExists(usersList, txtNomRegistre.text.toString(), txtContraRegistre.text.toString())

            if (x == true) {

                val user = User(txtNomRegistre.text.toString(), txtContraRegistre.text.toString(),0, true, "g", 27, true)

                val intent = Intent(this, DifficultActivity::class.java)
                intent.putExtra("provisional", user)

                startActivity(intent)
            } else {

                Toast.makeText(applicationContext, "Usruario o contrasnya incorrectos", Toast.LENGTH_SHORT).show()

            }*/

        }

    btnObrirRegistre.setOnClickListener() {

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

    }

}
    fun getUsers(): MutableList<User> {

        val jsonFilePath = getFilesDir().toString() + "/JSONS/USUARIS_app.json"
        val jsonFile = FileReader(jsonFilePath)
        val listUsersType = object: TypeToken<MutableList<User>>() {}.type
        val users: MutableList<User> =  Gson().fromJson(jsonFile, listUsersType)

        return users
    }

    /*fun userExists(list: MutableList<User>, userName: String, password: String): Boolean {

        var toReturn = false;

        do {

            var i = 0

            if (userName == list[i].nom && password == list[i].contrasenya) {

                toReturn = true;

            }

            i++

        } while (i<list.count())

        return  toReturn

    }*/
}