package space.ava.restiloc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import space.ava.restiloc.R
import space.ava.restiloc.classes.PreePost

class ExpertiseAdapter(private val expertiseList: MutableList<PreePost>) : RecyclerView.Adapter<ExpertiseAdapter.ExpertiseViewHolder>(){

    inner class ExpertiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.label_expertise)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_expertise)
        val deleteExpertiseButton: ImageView = itemView.findViewById(R.id.delete_expertise_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertiseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_expertise, parent, false)
        return ExpertiseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpertiseViewHolder, position: Int) {
        val currentExpertise = expertiseList[position]
        holder.labelTextView.text = currentExpertise.label
        holder.descriptionTextView.text = currentExpertise.description

        holder.deleteExpertiseButton.setOnClickListener {
            expertiseList.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "Expertise supprimée", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = expertiseList.size
}