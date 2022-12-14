package com.example.filmz_project

import RankingAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking_screen)

        val lstRanking = findViewById<ListView>(R.id.lstRanking)
        val btnContinuarRanking = findViewById<Button>(R.id.btnContinuarRanking)
        intent = getIntent()
        var user = intent.getSerializableExtra(Keys.constKeys.RESULT_TO_RANKING) as User
        //val user = User("jugador actual",null,19,'h',1600,true,2,null);
        var ranking = getRanking(this)
        ranking.add(user)
        ranking.sortByDescending { it.puntuacio }
        var finalRanking = formattingRanking(ranking)

        var adapter = RankingAdapter(this,R.layout.ranking_item,finalRanking)
        lstRanking.adapter=adapter;
        var animation : Animation = AnimationUtils.loadAnimation(this,R.anim.move_down)
        lstRanking.startAnimation(animation)
        btnContinuarRanking.startAnimation(animation)

        btnContinuarRanking.setOnClickListener(){
            ranking=resetRankingPlayer(ranking)
            saveRanking(this,ranking)
            var intent = Intent(this,FinalActivity::class.java)
            user.puntuacio=0
            user.jugadorActual=true
            intent.putExtra(Keys.constKeys.TO_FINAL, user)
            startActivity(intent)
        }
    }

    fun getRanking(context: Context): MutableList<User>{

        val jsonFilePath = context.getFilesDir().toString() + "/JSONS/ranking.json"
        val jsonFile = FileReader(jsonFilePath)
        val listRankingType = object: TypeToken<MutableList<User>>() {}.type
        val ranking: MutableList<User> = Gson().fromJson(jsonFile, listRankingType)
        return ranking
    }
    fun saveRanking(context: Context, ranking: List<User>){
        val jsonFilePath = context.getFilesDir().toString() + "/JSONS/ranking.json"
        val jsonFile = FileWriter(jsonFilePath)
        var gson = Gson()
        var jsonElement = gson.toJson(ranking)
        jsonFile.write(jsonElement)
        jsonFile.close()
    }
    /**
     * Funci?? que ordena el ranking segons la puntuaci??,mostra nom??s el top 5 i col??loca a l'usuari
     * al final del ranking si no es troba en el top 5
     * */
    fun formattingRanking(rankingParameter: MutableList<User>): MutableList<User> {
        var ranking = rankingParameter
        ranking.sortByDescending { it.puntuacio }
        ranking = asignarPosicio(ranking)
        val jugadorPosicio = jugadorActualPosicion(ranking)

        if(jugadorPosicio>4){
            ranking[4]=ranking[jugadorPosicio]
        }
        var finalRanking = mutableListOf<User>()
        for (j in 0..4){
          finalRanking.add(ranking[j])
        }
        return finalRanking
    }
    /**
     * Funci?? que recorre tota la llista del ranking i retorna la posici?? del jugador actual
     * */
    fun jugadorActualPosicion(ranking: MutableList<User>): Int{
        var posicio: Int = 0
        var userActual: Boolean = false
        do
        {
            if(ranking[posicio].jugadorActual){
                userActual=true
            }
            else
            {
                posicio++;
            }
        }while(!userActual)

        return posicio
    }
    /**
     * Funci?? que asigna la posici?? en el ranking a tots els elements del ranking
     * */
    fun asignarPosicio(ranking: MutableList<User>): MutableList<User> {
        var i: Int = 0
        for (item in ranking){
            item.posicionRanking = i+1
            i++
        }
        return ranking
    }
    /**
     * Funci?? que reseteja tot el ranking per posar en false al jugador actual en el ranking general
     * */
    fun resetRankingPlayer(ranking: MutableList<User>): MutableList<User> {
        for (item in ranking){
            item.jugadorActual=false;
        }
        return ranking
    }
}