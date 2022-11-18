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

        val user = User("jugador actual",null,19,true,'h',1600,true,2,null);
        var ranking = getRanking(this)
        ranking.add(user)
        ranking.sortByDescending { it.puntuacio }
        val finalRanking = formattingRanking(ranking)


        val adapter = RankingAdapter(this,R.layout.ranking_item,finalRanking)
        lstRanking.adapter=adapter;
        val animation : Animation = AnimationUtils.loadAnimation(this,R.anim.move_down)
        lstRanking.startAnimation(animation)
        btnContinuarRanking.startAnimation(animation)

        btnContinuarRanking.setOnClickListener(){
            ranking=resetRankingPlayer(ranking)
            saveRanking(this,ranking)
            val intent = Intent(this,FinalActivity::class.java)
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
    fun asignarPosicio(ranking: MutableList<User>): MutableList<User> {
        var i: Int = 0
        for (item in ranking){
            item.posicionRanking = i+1
            i++
        }
        return ranking
    }

    fun resetRankingPlayer(ranking: MutableList<User>): MutableList<User> {
        for (item in ranking){
            item.jugadorActual=false;
        }
        return ranking
    }
}