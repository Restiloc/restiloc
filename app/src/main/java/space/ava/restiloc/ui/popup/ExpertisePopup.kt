package space.ava.restiloc.ui.popup

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.ava.restiloc.ApiClient
import space.ava.restiloc.R
import space.ava.restiloc.SessionManager
import space.ava.restiloc.classes.ApiResponse
import space.ava.restiloc.classes.Mission
import space.ava.restiloc.classes.PreePost
import space.ava.restiloc.ui.adapter.ExpertiseAdapter

class ExpertisePopup(private val meetingAdapter: Context, private val currentMeeting: Mission) : Dialog(
    meetingAdapter
)  {

    val expertiseList = mutableListOf<PreePost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_expertise)

        val addExpertiseButton = findViewById<Button>(R.id.add_expertise_button)
        addExpertiseButton.setOnClickListener {
            addExpertiseView()
        }

        val sendExpertiseButton = findViewById<Button>(R.id.send_expertise_button)
        sendExpertiseButton.setOnClickListener {
            sendExpertiseListToApi()
        }
    }

    private fun addExpertiseView() {
        // afficher le recycler view en ajoutant les inputs
        val label = findViewById<TextView>(R.id.editTextExpertise).text
        val description = findViewById<TextView>(R.id.editTextExpertise2).text

        if (label.isEmpty() || description.isEmpty()) {
            Toast.makeText(meetingAdapter, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            val expertise = PreePost(label.toString(), description.toString(), currentMeeting.id)
            expertiseList.add(expertise)
            val expertiseRecyclerView = findViewById<RecyclerView>(R.id.vertical_recycler_view)
            expertiseRecyclerView.layoutManager = LinearLayoutManager(meetingAdapter)
            expertiseRecyclerView.adapter = ExpertiseAdapter(expertiseList)
            Toast.makeText(meetingAdapter, "Expertise ajoutée", Toast.LENGTH_SHORT).show()
            // vider les champs
            findViewById<TextView>(R.id.editTextExpertise).text = ""
            findViewById<TextView>(R.id.editTextExpertise2).text = ""

        }
    }


    private fun sendExpertiseListToApi() {
        if (expertiseList.isEmpty()) {
            Toast.makeText(meetingAdapter, "Veuillez ajouter une expertise", Toast.LENGTH_SHORT).show()
            return
        }
        else {
        val apiService = ApiClient.apiService
        val sessionManager = SessionManager(meetingAdapter)
        for (i in 0 until expertiseList.size) {
            val expertise = expertiseList[i]
            apiService.addExpertise("Bearer ${sessionManager.fetchAuthToken()}", expertise)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(
                            meetingAdapter,
                            "Impossible d'envoyer les données",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ApiResponse>,
                        response: Response<ApiResponse>
                    ) {
                        // si $clientCanceled est true écrire oui sinon non
                        Toast.makeText(meetingAdapter, "Expertise envoyée", Toast.LENGTH_SHORT)
                            .show()
                    }
                })


            }
            expertiseList.clear()
            dismiss()
        }
    }
}