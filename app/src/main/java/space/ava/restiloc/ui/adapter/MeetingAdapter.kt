package space.ava.restiloc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.ava.restiloc.MainActivity
import space.ava.restiloc.R
import space.ava.restiloc.classes.Mission
import space.ava.restiloc.classes.Vehicle

class MeetingAdapter (
    private val planningList : List<Mission>,
    private val layoutId: Int) : RecyclerView.Adapter<MeetingAdapter.ViewHolder>()
{
    //Boite pour ranger tous les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val MeetingImage = view.findViewById<ImageView>(R.id.image_item)
        val MeetingName:TextView? = view.findViewById(R.id.name_item)
        val MeetingDescription:TextView? = view.findViewById(R.id.description_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Recuperer les informations de la plante
        val currentMeeting = planningList[position]

        // mettre a jour le nom du vehicule
        holder.MeetingName?.text = currentMeeting.nameExpertFile
        holder.MeetingDescription?.text = currentMeeting.dateMission
    }

    override fun getItemCount(): Int = planningList.size
}