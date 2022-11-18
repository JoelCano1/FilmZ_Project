package com.example.filmz_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ResultAdapter(context: Context, val layout: Int, val generes: MutableList<User>):
    ArrayAdapter<User>(context, layout, generes) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView !=null){
            view = convertView
        }
        else{
            view =
                LayoutInflater.from(getContext()).inflate(layout, parent, false)
        }
        bindRanking(view,generes[position],position+1)
        return view
    }

    fun bindRanking(view: View, user: User, position: Int){
        val encerts = view.findViewById<TextView>(R.id.LblEncerts)



    }
}