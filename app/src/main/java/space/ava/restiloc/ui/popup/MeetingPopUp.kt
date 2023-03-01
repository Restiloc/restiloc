package space.ava.restiloc.ui.popup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import space.ava.restiloc.R
import space.ava.restiloc.classes.Mission

class MeetingPopUp(private val meetingAdapter: Context, private val currentMeeting: Mission) : Dialog(
    meetingAdapter
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_meeting_detail)
        // mettre a la taille de l'écran
        window?.setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )
        setupComponents()
    }

    private fun setupComponents() {
        // actualiser les données de la popup
        val namePopup = findViewById<TextView>(R.id.pop_up_meeting_detail_title)
        namePopup.text = currentMeeting.folder

        // horaires
        val startPopup = findViewById<TextView>(R.id.pop_up_date_detail)
        // en enlevant les secondes de la date

        startPopup.text = currentMeeting.startedAt.substring(0, 5)

        // type de rendez-vous
        val typeRDV = findViewById<TextView>(R.id.pop_up_type_detail)
        typeRDV.text = currentMeeting.type


        // immatriculation
        val immat = findViewById<TextView>(R.id.pop_up_immatriculation_detail)
        immat.text = currentMeeting.vehicle.licencePlate.substring(0, 7)


        // model de vehicule
        val model = findViewById<TextView>(R.id.pop_up_model_detail)
        model.text = currentMeeting.vehicle.model.brand

        // marque de vehicule
        val brand = findViewById<TextView>(R.id.pop_up_marque_detail)
        brand.text = currentMeeting.vehicle.model.label


        // couleur de vehicule
        val color = findViewById<TextView>(R.id.pop_up_car_color_detail)
        color.text = currentMeeting.vehicle.color

        // close button
        val close = findViewById<ImageView>(R.id.close_button)
        close.setOnClickListener {
            dismiss()
        }
    }
}