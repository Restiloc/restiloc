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

        apiService.getCurrentExpert("Bearer ${sessionManager.fetchAuthToken()}").enqueue(object : Callback<Expert> {
            override fun onResponse(call: Call<Expert>, response: Response<Expert>) {
                val response = response.body()
                Log.d("Profile", response.toString())
                expert = response!!
                val name = root.findViewById<TextView>(R.id.settings_name)
                val firstname = root.findViewById<TextView>(R.id.settings_firstname)
                val email = root.findViewById<TextView>(R.id.settings_email)
                val phone = root.findViewById<TextView>(R.id.settings_tel)
                name.text = expert.lastName
                firstname.text = expert.firstName
                email.text = expert.email
                phone.text = expert.phoneNumber
            }
            override fun onFailure(call: Call<Expert>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        val editButton = root.findViewById<Button>(R.id.settings_save)

        editButton.setOnClickListener{
            val updateRequest = UpdateRequest(
                root.findViewById<TextView>(R.id.settings_name).text.toString(),
                root.findViewById<TextView>(R.id.settings_firstname).text.toString(),
                root.findViewById<TextView>(R.id.settings_email).text.toString(),
                Integer.parseInt(root.findViewById<TextView>(R.id.settings_tel).text.toString())
            )
            Log.d("Profile", expert.id.toString())
            Log.d("Profile", updateRequest.toString());
            apiService.updateExpert(
                token = "Bearer ${sessionManager.fetchAuthToken()}",
                id = expert.id.toString(),
                updateRequest = updateRequest
            ).enqueue(object : Callback<UpdateResponse> {
                override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                    val updateResponse = response.body()
                    Log.d("Profile", "Update response : ${updateResponse.toString()}")
                }
                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

        val logoutButton = root.findViewById<Button>(R.id.logout)

        logoutButton.setOnClickListener{
            apiService.logout("Bearer ${sessionManager.fetchAuthToken()}")
                .enqueue(object : Callback<LogoutResponse> {
                    override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                        val logoutResponse = response.body()
                        Log.d("Logout", logoutResponse.toString())
                        if (logoutResponse?.status == true) {
                            sessionManager.setLogin(false)
                            // Redirection vers la page de login
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Log.d("LogoutActivity", "Error during logout: ${logoutResponse?.message}")
                        }
                    }
                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                        TODO("Not yet implemented")
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
