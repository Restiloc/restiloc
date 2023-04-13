package space.ava.restiloc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.ava.restiloc.R
import space.ava.restiloc.classes.Stats

class StatsAdapter(
    private val statsList: List<Stats>,
    private val layoutId: Int) : RecyclerView.Adapter<StatsAdapter.ViewHolder>(){

        // Boite pour ranger tous les composants à controler
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val statsImage: ImageView = view.findViewById(R.id.image_graph)
            val statsName: TextView? = view.findViewById(R.id.reason_item)
          val statsDescription: TextView? = view.findViewById(R.id.count_item)
        }

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater
                        .from(parent.context)
                       .inflate(layoutId, parent, false)

             return ViewHolder(view)
        }

          override fun onBindViewHolder(holder: ViewHolder, position: Int) {

                // Recuperer les informations des stats
                  val currentStats = statsList[position]
                 // val reasons = currentStats.reason.joinToString(", ") { it.label }

                // test pour ajouter une image tirée d'internet
                // Glide.with(holder.meetingImage.context)
                //    .load(currentMeeting.vehicle.route)
                //    .into(holder.meetingImage)

              // Mettre à jour les composants
              holder.statsName?.text = currentStats.reason.label
              holder.statsDescription?.text = currentStats.count.toString()



                // interaction avec le composant
                // holder.itemView.setOnClickListener {
                        // afficher une popup
                   //     MeetingPopUp(holder.itemView.context, currentMeeting).show()
}
 override fun getItemCount(): Int = statsList.size
}

