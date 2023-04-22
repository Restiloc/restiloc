package space.ava.restiloc.ui.settings
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.*
import space.ava.restiloc.classes.*
import space.ava.restiloc.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var sessionManager: SessionManager


    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sessionManager = SessionManager(requireContext())

        val settingsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSettings
        settingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restiloc.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

        var expert: Expert = Expert(0, "", "", "", "", "", "");

        try {
            apiService.getCurrentExpert("Bearer ${sessionManager.fetchAuthToken()}").enqueue(object : Callback<Expert> {
                override fun onResponse(call: Call<Expert>, response: Response<Expert>) {
                    if (response.isSuccessful) {
                        expert = response.body()!!
                    }
                    Log.d("Profile", "Expert : $expert")
                    root.findViewById<TextView>(R.id.settings_name).text = expert.lastName
                    root.findViewById<TextView>(R.id.settings_firstname).text = expert.firstName
                    root.findViewById<TextView>(R.id.settings_email).text = expert.email
                    root.findViewById<TextView>(R.id.settings_tel).text = expert.phoneNumber
                }
                override fun onFailure(call: Call<Expert>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        } catch (e: Exception) {
            Log.d("Profile", "Error : ${e.message}")
        }

        val editButton = root.findViewById<Button>(R.id.settings_save)

        try {
            editButton.setOnClickListener{
                val updateRequest = UpdateRequest(
                    root.findViewById<TextView>(R.id.settings_name).text.toString(),
                    root.findViewById<TextView>(R.id.settings_firstname).text.toString(),
                    root.findViewById<TextView>(R.id.settings_email).text.toString(),
                    root.findViewById<TextView>(R.id.settings_tel).text.toString()
                )
                Log.d("Profile", expert.id.toString())
                Log.d("Profile", updateRequest.toString());
                apiService.updateExpert(
                    token = "Bearer ${sessionManager.fetchAuthToken()}",
                    id = expert.id.toString(),
                    updateRequest = updateRequest
                ).enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        val updateResponse = response.body()
                        Log.d("Profile", "Update response : ${updateResponse.toString()}")
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
        } catch (e: Exception) {
            Log.d("Profile", "Error : ${e.message}")
        }

        val logoutButton = root.findViewById<Button>(R.id.logout)

        logoutButton.setOnClickListener{
            Log.d("Logout", "Logout button clicked")

            apiService.logout("Bearer ${sessionManager.fetchAuthToken()}")
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        val logoutResponse = response.body()
                        Log.d("Logout", logoutResponse.toString())

                        sessionManager.setLogin(false)
                        // Redirection vers la page de login
                        val intent = Intent(context, LandingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.d("Logout", "Error during logout request: ${t.message}")
                        sessionManager.setLogin(false)
                        // Redirection vers la page de login
                        val intent = Intent(context, LandingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                })
            sessionManager.setLogin(false)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}