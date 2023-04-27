package space.ava.restiloc.ui.popup

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.*
import com.google.android.gms.common.api.Api
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.ava.restiloc.ApiClient
import space.ava.restiloc.MainActivity
import space.ava.restiloc.R
import space.ava.restiloc.SessionManager
import space.ava.restiloc.classes.*


class MeetingPopUp(private val meetingAdapter: Context, private val currentMeeting: Mission, private val reasonsList: List<Reason>) : Dialog(
    meetingAdapter
) {

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_meeting_detail)

        // mettre a la taille de l'écran
        window?.setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )
        val apiService = ApiClient.apiService
        val sessionManager = SessionManager(meetingAdapter)
        setupComponents()
        // Regarde si la mission est un garage ou un client
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

        // j'appuie sur le bouton "expertiser le vehicule

        val expertise = findViewById<Button>(R.id.button)
        expertise.setOnClickListener{
            // afficher la popup d'expertise
            ExpertisePopup(meetingAdapter, currentMeeting).show()

        }

        // j'appuie sur le bouton cloturer la mission
        val closeMission = findViewById<Button>(R.id.buttonCloseMission)
        val request = MissionRequest(true)
        closeMission.setOnClickListener{
            // Appel de l'API pour cloturer la mission
            apiService.closeMission(
                token = "Bearer ${sessionManager.fetchAuthToken()}",
                id = currentMeeting.id.toString(), request).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(meetingAdapter, "La mission a été cloturée", Toast.LENGTH_SHORT).show()
                        // retourne à la page d'accueil
                        val intent = Intent(meetingAdapter, MainActivity::class.java)
                        meetingAdapter.startActivity(intent)
                        dismiss()
                    }
                    else {
                        Toast.makeText(meetingAdapter, "La mission n'a pas pu être cloturée", Toast.LENGTH_SHORT).show()
                        Log.d("API", "onResponse: ${response.message()} ${request.toString()}")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(meetingAdapter, "La mission n'a pas pu être cloturée", Toast.LENGTH_SHORT).show()
                    Log.d("API", "onFailure: ${t.message}")
                }
            })
        }

        // J'appuie sur le bouton "Indisponibilité"
        val unavaibilityButton = findViewById<Button>(R.id.buttonUnavaibility)
        unavaibilityButton.setOnClickListener {
            val builder = AlertDialog.Builder(meetingAdapter)

            builder.setTitle("Indisponibilité du véhicule")


            // vérifier si la liste des raisons est vide
            if (reasonsList.isNotEmpty()) {
                // convertir la liste de raisons en tableau de chaînes
                val reasons = reasonsList.map { it.label }.toTypedArray()
                var selectedReason = 0



                builder.setSingleChoiceItems(reasons, selectedReason) { dialog, which ->
                    // mettre à jour la raison sélectionnée
                    selectedReason = which
                }


                builder.setPositiveButton("OK") { dialog, which ->
                    // récupérer la raison sélectionnée
                    val selectedReasonText = reasonsList[selectedReason].label
                    // récupérer la valeur de la checkbox
                    val clientCanceled = true

                    val unavailability = Unavailability(
                        reason_id = reasonsList[selectedReason].id, // récupérer l'id de la raison sélectionné
                        mission_id = currentMeeting.id, // récupérer l'id de la réunion actuelle
                        customerResponsible = clientCanceled, // récupérer la valeur de la checkbox
                    )

                    apiService.postUnavailability("Bearer ${sessionManager.fetchAuthToken()}", unavailability)
                        .enqueue(object : Callback<ApiResponse> {
                            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                Toast.makeText(meetingAdapter, "Impossible d'envoyer les données", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                // si $clientCanceled est true écrire oui sinon non
                                Toast.makeText(meetingAdapter, "Raison envoyée : $selectedReasonText, Annulé par le client : $clientCanceled ", Toast.LENGTH_SHORT).show()

                            }
                        })
                }
                builder.setNegativeButton("Annuler", null)
                val dialog = builder.create()
                dialog.show()
            } else {
                // afficher un message d'erreur si la liste des raisons est vide
                Toast.makeText(meetingAdapter, "Impossible de récupérer les raisons d'indisponibilité", Toast.LENGTH_SHORT).show()
            }

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

        // nom de l'assurance
        val nameInsurance = findViewById<TextView>(R.id.pop_up_car_name_insurance_detail)
        nameInsurance.text = currentMeeting.vehicle.contract.insurance.name

        // adresse de l'assurance
        val adress = findViewById<TextView>(R.id.pop_up_car_adress_insurance_detail)
        adress.text = currentMeeting.vehicle.contract.insurance.addressNumber + ", " + currentMeeting.vehicle.contract.insurance.street

        // ville de l'assurance
        val city = findViewById<TextView>(R.id.pop_up_car_city_insurance_detail)
        city.text = currentMeeting.vehicle.contract.insurance.postalCode + " " + currentMeeting.vehicle.contract.insurance.city

        // téléphone de l'assurance
        val number = findViewById<TextView>(R.id.pop_up_car_number_insurance_detail)
        number.text = currentMeeting.vehicle.contract.insurance.phoneNumber


        // close button
        val close = findViewById<ImageView>(R.id.close_button)
        close.setOnClickListener {
            dismiss()
        }
    }
    
}