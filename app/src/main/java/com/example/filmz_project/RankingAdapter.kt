import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.filmz_project.R
import com.example.filmz_project.R.*
import com.example.filmz_project.R.string.*
import com.example.filmz_project.User

class RankingAdapter(context: Context, val layout: Int, val ranking: MutableList<User>):
    ArrayAdapter<User>(context, layout, ranking)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView !=null){
            view = convertView
        }
        else{
            view =
                LayoutInflater.from(getContext()).inflate(layout, parent, false)
        }
        bindRanking(view,ranking[position],position+1)
        return view
    }

    fun bindRanking(view: View, user: User,position: Int){
        val rankingPosition = view.findViewById<TextView>(id.rankingPosition)
        val rankingElement = view.findViewById<LinearLayout>(id.rankingElement)
        var rankingLinearPos = view.findViewById<LinearLayout>(R.id.linearRankingPos)

        if(user.posicionRanking!! in 1..9)
        {
            rankingLinearPos.setPadding(47,0,0,7)
        }
        else if (user.posicionRanking!! in 10..99)
        {
            rankingLinearPos.setPadding(35,0,0,7)
        }
        else if(user.posicionRanking!! in 100..999)
        {
            rankingLinearPos.setPadding(23,0,0,7)
        }
        //Condicional que posara un fons al element del ranking del jugador actual ,
        // en cas que no un element no sigui l'element actual el fons serà transparent.
        if(user.jugadorActual)
        {
            rankingElement.setBackgroundColor(Color.parseColor("#E34C42"));
        }
        else
        {
            rankingElement.setBackgroundColor(Color.TRANSPARENT);
        }
        rankingPosition.text = user.posicionRanking.toString()
        //when que establirà una imatge determinada per cada una de les tres posicions del ranking
        val rankingInfo = view.findViewById<LinearLayout>(id.txtRanking)
        when(position)
        {
            1 -> rankingInfo.setBackgroundResource(drawable.rankinginfofirst)
            2 -> rankingInfo.setBackgroundResource(drawable.rankinginfosecond)
            3 -> rankingInfo.setBackgroundResource(drawable.rankinginfothird)
            else -> {
                rankingInfo.setBackgroundResource(drawable.rankinginfoother)
            }
        }

        val rankingNom = view.findViewById<TextView>(id.txtNomJugadorRanking)
        rankingNom.text = user.nom
        val rankingPuntuacio = view.findViewById<TextView>(id.txtPuntuacioRanking)
        rankingPuntuacio.text = user.puntuacio.toString()
        val rankingDificultat= view.findViewById<TextView>(id.txtDificultatRanking)
        when(user.difficult)
        {
            1 -> rankingDificultat.text = context.getString(R.string.difficult_screen_buttonTextFacil)
            2 -> rankingDificultat.text = context.getString(R.string.difficult_screen_buttonTextMitja)
            3 -> rankingDificultat.text = context.getString(R.string.difficult_screen_buttonTextDificil)
        }
    }
}