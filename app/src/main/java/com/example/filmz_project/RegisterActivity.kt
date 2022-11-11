package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        crearUsuari()
        iniciarSessio()
    }

    private fun crearUsuari() {
        val campNom = findViewById<EditText>(R.id.EdtTxtNomRegistre)
        val campContra = findViewById<EditText>(R.id.EdtTxtContrasenyaRegistre)
        val campRepeatContra = findViewById<EditText>(R.id.EdtTxtRepetirContrasenyaRegistre)
        val campEdat = findViewById<EditText>(R.id.EdtTxtEdatRegistre)
        val campSexeHome = findViewById<RadioButton>(R.id.RdBtnMaleRegistre)
        val campSexeDona = findViewById<RadioButton>(R.id.RdBtnFemaleRegistre)
        val campSexeAltre = findViewById<RadioButton>(R.id.RdBtnAltreRegistre)
        val campEstudia = findViewById<RadioButton>(R.id.RdBtnEstudiaRegistre)
        val campNoEstudia = findViewById<RadioButton>(R.id.RdBtnNoEstudiaRegistre)
        val campRobot = findViewById<CheckBox>(R.id.CkBoxRobotRegistre)
        val campPolitica = findViewById<CheckBox>(R.id.CkBoxPolicyRegistre)
        val btnCrearUsuari = findViewById<Button>(R.id.BtnCrearUsuari)

        btnCrearUsuari.setOnClickListener() {
            if(revisarCampsBuits(campNom, campContra, campRepeatContra, campEdat, campRobot, campPolitica) == true) {
                if (revisarCampEdat(campEdat) == false) {
                    Toast.makeText(this, "El camp de l'edat ha de ser un enter!", Toast.LENGTH_LONG).show()
                } else {
                    if(aprovarContrasenya(campContra) == false) {
                        Toast.makeText(this, "La contrassenya ha de tenir:\n->Almenys 8 caràcters\n->Almenys 1 minúscula i 1 majuscula\n->Almenys 1 caràcter especial\n->Almenys 1 digit", Toast.LENGTH_LONG).show()
                    } else {
                        if(revisarContrasenyaIguals(campContra, campRepeatContra) == false) {
                            Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_LONG).show()
                        } else {
                            btnCrearUsuari.setOnClickListener() {
                                //Guardar Objecte d'usuari en el JSON

                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun revisarCampsBuits(campNom: EditText, campContra: EditText, campRepeatContra: EditText, campEdat: EditText, campRobot: CheckBox, campPolitica: CheckBox): Boolean{
        if(TextUtils.isEmpty(campNom.text)) {
            campNom.setError("No pot estar buit aquest camp")
            return false;
        }
        if(TextUtils.isEmpty(campContra.text)) {
            campContra.setError("No pot estar buit aquest camp")
            return false;
        }
        if(TextUtils.isEmpty(campRepeatContra.text)) {
            campRepeatContra.setError("No pot estar buit aquest camp")
            return false;
        }
        if(TextUtils.isEmpty(campEdat.text)) {
            campEdat.setError("No pot estar buit aquest camp")
            return false;
        }
        if(!campRobot.isChecked) {
            campRobot.setError("Selecciona aquesta opció")
            return false
        }
        if(!campPolitica.isChecked) {
            campPolitica.setError("Selecciona aquesta opció")
            return false
        }
        return true
    }

    private fun revisarCampEdat(campEdat: EditText): Boolean {
        return campEdat.text.all { char -> char.isDigit() }
    }

    private fun aprovarContrasenya(campContra: EditText): Boolean {
        if (campContra.text.length < 8) return false
        if (campContra.text.filter { it.isDigit() }.firstOrNull() == null) return false
        if (campContra.text.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (campContra.text.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (campContra.text.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }
    private fun revisarContrasenyaIguals(campContra: EditText, campRepeatContra: EditText): Boolean {
        if (campContra.text == campRepeatContra.text) return true

        return false
    }

    private fun iniciarSessio() {
        val btnIniciSessio = findViewById<Button>(R.id.BtnIniciarSessioRegistre)

        btnIniciSessio.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}