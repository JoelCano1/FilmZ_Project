package com.example.filmz_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.mindrot.jbcrypt.BCrypt
import java.io.FileReader
import java.io.FileWriter

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        var usuaris = getUsers()

        crearUsuari(usuaris)
    }

    private fun getUsers(): MutableList<User> {
        val jsonFilePath = getFilesDir().toString() + "/JSONS/USUARIS_android.json"
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
        val campRobot = findViewById<CheckBox>(R.id.CkBoxRobotRegistre)
        val campPolitica = findViewById<CheckBox>(R.id.CkBoxPolicyRegistre)
        val btnCrearUsuari = findViewById<Button>(R.id.BtnCrearUsuari)

        btnCrearUsuari.setOnClickListener() {
            if(revisarCampsBuits(campNom, campContra, campRepeatContra, campEdat, campRobot, campPolitica) == true) {
                if (revisarCampEdat(campEdat) == false) {
                    Toast.makeText(this, resources.getText(R.string.register_screen_toast_edat_enter), Toast.LENGTH_LONG).show()
                } else {
                    if(aprovarContrasenya(campContra) == false) {
                        Toast.makeText(this, resources.getText(R.string.register_screen_toast_contrasenya_requisits), Toast.LENGTH_LONG).show()
                    } else {
                        if(revisarContrasenyaIguals(campContra, campRepeatContra) == false) {
                            Toast.makeText(this, resources.getText(R.string.register_screen_toast_repetir_contrasenya), Toast.LENGTH_LONG).show()
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
                                Toast.makeText(this, resources.getText(R.string.register_screen_toast_usuari_existent), Toast.LENGTH_LONG).show()
                            } else {
                                btnCrearUsuari.setOnClickListener() {
                                    //SEXE
                                    var sexe = 'H'
                                    if (campSexeHome.isChecked) {
                                        sexe
                                    } else if (campSexeDona.isChecked) {
                                        sexe = 'M'
                                    } else if (campSexeAltre.isChecked) {
                                        sexe = 'A'
                                    }
                                    //Guardar Objecte d'usuari en el JSON
                                    var hashPassword= Blowfish.Encrypt(campContra.text.toString())
                                    usuaris.add(User(campNom.text.toString(),hashPassword, Integer.parseInt(campEdat.text.toString()), sexe, 0, false, 2, null))

                                    saveUser(this, usuaris)

                                    val intentToLog = Intent(this, LoginActivity::class.java)
                                    //Pasar usuari a IniciSessio
                                    //intentToLog.putExtra(Keys.constKeys.REGISTER_TO_LOGIN, usuaris)
                                    startActivity(intentToLog)
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

    private fun saveUser(context: Context, usuaris: MutableList<User>) {
        val jsonFilePath = context.getFilesDir().toString() + "//JSONS/USUARIS_android.json"
        val jsonFile = FileWriter(jsonFilePath)
        var gson = Gson()
        var jsonElement = gson.toJson(usuaris)
        jsonFile.write(jsonElement)
        jsonFile.close()
    }

}