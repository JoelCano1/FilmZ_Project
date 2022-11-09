package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

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

            //if (txtNomRegistre.text.equals("a")) {

                val user = User(txtNomRegistre.text.toString(), 0, true, "g", 27, true)

                val intent = Intent(this, DifficultActivity::class.java)
                intent.putExtra("provisional", user)

                startActivity(intent)
            //} else {

                Toast.makeText(applicationContext, "El usuario no existe", Toast.LENGTH_SHORT)

            //}

        }

        btnObrirRegistre.setOnClickListener() {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

    }
}