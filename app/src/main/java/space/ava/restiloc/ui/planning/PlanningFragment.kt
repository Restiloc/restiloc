package space.ava.restiloc.ui.planning

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.ApiClient
import space.ava.restiloc.ApiClient.BASE_URL
import space.ava.restiloc.R
import space.ava.restiloc.SessionManager
import space.ava.restiloc.classes.*
import space.ava.restiloc.databinding.FragmentPlanningBinding
import space.ava.restiloc.ui.adapter.MeetingAdapter
import space.ava.restiloc.ui.adapter.MeetingItemDecoration
import java.io.ByteArrayOutputStream


class PlanningFragment() : Fragment() {

    private companion object {
        private const val TAKE_PHOTO_REQUEST_CODE = 123
    }
    private var _binding: FragmentPlanningBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            ViewModelProvider(this)[PlanningViewModel::class.java]
        _binding = FragmentPlanningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lateinit var sessionManager: SessionManager

        val photoButton = root.findViewById<Button>(R.id.photo_button)
        photoButton.setOnClickListener {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE)        }

        val apiService = ApiClient.apiService

        val planningList = ArrayList<Mission>()
        val planningNextList = ArrayList<Mission>()
        val reasonList = ArrayList<Reason>()

        // appel asynchrone de l'API
        lifecycleScope.launch {
            try {
                // recuperer le token de l'utilisateur
                sessionManager = SessionManager(requireContext())
                val missions = apiService.getInfos( "Bearer ${sessionManager.fetchAuthToken()}")
                val reasons = apiService.getReasons( "Bearer ${sessionManager.fetchAuthToken()}")
                val nextmission = apiService.getNextMission( "Bearer ${sessionManager.fetchAuthToken()}")
                Log.d("test", reasons.toString())

                if (missions != null) {
                    for (mission in missions) {
                        planningList.add(mission)
                    }
                }
                if (planningList.isEmpty()) {
                    val noMission : TextView = root.findViewById(R.id.nomission)
                    noMission.visibility = View.VISIBLE
                }

                if (nextmission != null) {
                    for (mission in nextmission) {
                        planningNextList.add(mission)
                    }
                }

                if (planningNextList.isEmpty()) {
                    val noMission2 : TextView = root.findViewById(R.id.nomission2)
                    noMission2.visibility = View.VISIBLE
                }


                for (reason in reasons) {
                    reasonList.add(reason)
                }

                val verticalRecyclerView2 = root.findViewById<RecyclerView>(R.id.vertical_recycler_view2)
                verticalRecyclerView2?.adapter = MeetingAdapter(planningNextList, reasonList, R.layout.item_horizontal_folder)

                val verticalRecyclerView = root.findViewById<RecyclerView>(R.id.vertical_recycler_view)

                verticalRecyclerView?.adapter = MeetingAdapter(planningList, reasonList, R.layout.item_horizontal_folder)

                verticalRecyclerView?.addItemDecoration(MeetingItemDecoration())
                Log.d("test", missions.toString())

            } catch (e: Exception) {
                // PrintStackTrace pour afficher l'erreur
                e.printStackTrace()
                // verifie dans quel condition je suis
                Log.d("test", "testError")
            }
        }


        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoBitmap = data?.extras?.get("data") as Bitmap?
            val photoImageView = view?.findViewById<ImageView>(R.id.imageView)
            photoImageView?.setImageBitmap(photoBitmap)

            lifecycleScope.launch {
                try {
                    // Récupérer le token de l'utilisateur
                    val sessionManager = SessionManager(requireContext())
                    val authToken = sessionManager.fetchAuthToken()

                    // Convertir l'image en tableau de bytes
                    val photoByteArray = bitmapToByteArray(photoBitmap?: return@launch)

                    // Créer une instance de Retrofit
                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    // Créer une instance du service API
                    val apiService = ApiClient.apiService

                    // Envoyer la requête POST avec l'image
                    val response = apiService.uploadPhoto("Bearer $authToken",
                        photoByteArray.toRequestBody(
                            "image/jpeg".toMediaTypeOrNull(),
                            0,
                            photoByteArray.size
                        )
                    )

                    // Traiter la réponse de l'API
                    if (response.isExecuted) {
                        // Succès, traiter la réponse
                        Toast.makeText(requireContext(), "Photo envoyée avec succès", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Erreur lors de l'envoi de la photo", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // Gérer les exceptions
                }
            }
        }
    }
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
