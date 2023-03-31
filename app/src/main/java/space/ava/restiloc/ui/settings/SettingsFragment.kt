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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.*
import space.ava.restiloc.classes.LoginRequest
import space.ava.restiloc.classes.LoginResponse
import space.ava.restiloc.classes.LogoutResponse
import space.ava.restiloc.databinding.FragmentSettingsBinding
import space.ava.restiloc.ui.adapter.MeetingAdapter
import space.ava.restiloc.ui.adapter.MeetingItemDecoration

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

        val logoutButton = root.findViewById<Button>(R.id.logout)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restiloc.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)

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
                            Log.d("LoginActivity", "Error during logout: ${logoutResponse?.message}")
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