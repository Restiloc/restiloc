package space.ava.restiloc.ui.popup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

        if (currentMeeting.type == "Garage") {
            val longitude = currentMeeting.garage.longitude.toDouble()
            val latitude = currentMeeting.garage.latitude.toDouble()
            val location = LatLng(latitude, longitude)
            val titleMap = currentMeeting.garage.name

            setupMap(savedInstanceState, location, titleMap)
        }
        else {
            val longitude = currentMeeting.client.longitude.toDouble()
            val latitude = currentMeeting.client.latitude.toDouble()
            val location = LatLng(latitude, longitude)
            val titleMap = currentMeeting.client.lastName

            setupMap(savedInstanceState, location, titleMap)
        }

    }
    private fun setupMap(savedInstanceState: Bundle?, location: LatLng, titleMap: String) {
     val mapView = findViewById<MapView>(R.id.map_view)
     mapView.onCreate(savedInstanceState)
    mapView.getMapAsync { map ->
        // Ajouter le marker à l'emplacement de l'adresse
        val markerOptions = MarkerOptions().position(location).title(titleMap)
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
    }
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
        model.text = currentMeeting.vehicle.model.label

        // marque de vehicule
        val brand = findViewById<TextView>(R.id.pop_up_marque_detail)
        brand.text = currentMeeting.vehicle.model.brand


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