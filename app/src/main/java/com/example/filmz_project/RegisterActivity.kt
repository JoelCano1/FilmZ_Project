package com.example.filmz_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        var usuaris = getUsers()

        crearUsuari(usuaris)
        iniciarSessio()
    }

    private fun getUsers(): MutableList<User> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/USUARIS_app.json"
        val jsonFile = FileReader(jsonFilePath)
        val listUsuaris = object: TypeToken<MutableList<User>>() {}.type
        val usuaris: MutableList<User> = Gson().fromJson(jsonFile, listUsuaris)
        return usuaris
    }

    private fun crearUsuari(usuaris: MutableList<User>) {
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
                            var usuariRepetit = false
                            var count = 0
                            while (usuariRepetit == false && count < usuaris.size) {
                                if (campNom.text.toString() == usuaris.get(count).nom) {
                                    usuariRepetit = true
                                }
                                count++
                            }
                            if (usuariRepetit == true) {
                                Toast.makeText(this, "JA EXISTEIX UN USUARI AMB AQUEST NOM", Toast.LENGTH_LONG).show()
                            } else {
                                btnCrearUsuari.setOnClickListener() {
                                    //Si el check box estudia esta checked --> estudia es igual true...
                                    //Guardar Objecte d'usuari en el JSON
                                    usuaris.add(User(campNom.text.toString(), campContra.text.toString(), Integer.parseInt(campEdat.text.toString()), estudia))

                                    val intent = Intent(this, LoginActivity::class.java)
                                    //Pasar llista usuaris
                                    startActivity(intent)
                                }
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
        if (campContra.text.toString() == campRepeatContra.text.toString()) return true

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