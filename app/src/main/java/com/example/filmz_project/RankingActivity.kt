package com.example.filmz_project

import RankingAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ListView

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking_screen)

        val lstRanking = findViewById<ListView>(R.id.lstRanking)
        val btnContinuarRanking = findViewById<Button>(R.id.btnContinuarRanking)

        val user = User("jugador actual",null,19,true,'h',1500,true,2,null);
        var ranking = getRanking()
        ranking.add(user)
        ranking = orderRanking(ranking)


        val adapter = RankingAdapter(this,R.layout.ranking_item,ranking)
        lstRanking.adapter=adapter;
        val animation : Animation = AnimationUtils.loadAnimation(this,R.anim.move_down)
        lstRanking.startAnimation(animation)
        btnContinuarRanking.startAnimation(animation)

        btnContinuarRanking.setOnClickListener(){
            ranking=resetRankingPlayer(ranking)
            val intent = Intent(this,FinalActivity::class.java)
            startActivity(intent)
        }


    }


    fun getRanking(): MutableList<User>{
        return mutableListOf<User>(
            User("prueba",null,19,true,'h',1000,false,1,null),
            User("prueba2",null,20,true,'h',1200,false,2,null),
            User("prueba3",null,19,true,'h',1300,false,3,null),
            User("prueba4",null,19,true,'d',1400,false,3,null),
            User("prueba4",null,19,true,'d',1500,false,3,null),
            User("prueba4",null,19,true,'h',1600,false,3,null)
        )
    }

    fun orderRanking(rankingParameter: MutableList<User>): MutableList<User> {
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