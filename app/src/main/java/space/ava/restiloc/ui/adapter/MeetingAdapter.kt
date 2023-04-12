package space.ava.restiloc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.ava.restiloc.R
import space.ava.restiloc.classes.Mission
import space.ava.restiloc.classes.Reason
import space.ava.restiloc.ui.popup.MeetingPopUp


class MeetingAdapter (
    private val planningList : List<Mission>,
    private val reasonList : List<Reason>,
    private val layoutId: Int) : RecyclerView.Adapter<MeetingAdapter.ViewHolder>()
{

    // Boite pour ranger tous les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //val meetingImage: ImageView = view.findViewById(R.id.image_item)
        val meetingName:TextView? = view.findViewById(R.id.name_item)
        val meetingDescription:TextView? = view.findViewById(R.id.description_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Recuperer les informations du planning
        val currentMeeting = planningList[position]

        // test pour ajouter une image tirée d'internet
        //Glide.with(holder.meetingImage.context)
        //    .load(currentMeeting.vehicle.route)
        //    .into(holder.meetingImage)
        holder.meetingName?.text = currentMeeting.folder
        holder.meetingDescription?.text = currentMeeting.startedAt.substring(0, 5)



        // interaction avec le composant
        holder.itemView.setOnClickListener {
            // afficher une popup
            MeetingPopUp(holder.itemView.context, currentMeeting, reasonList).show()

        }
    }

    override fun getItemCount(): Int = planningList.size
}