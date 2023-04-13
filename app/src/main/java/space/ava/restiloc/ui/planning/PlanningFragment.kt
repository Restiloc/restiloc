package space.ava.restiloc.ui.planning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.ApiClient
import space.ava.restiloc.ApiInterface
import space.ava.restiloc.R
import space.ava.restiloc.SessionManager
import space.ava.restiloc.classes.*
import space.ava.restiloc.databinding.FragmentPlanningBinding
import space.ava.restiloc.ui.adapter.MeetingAdapter
import space.ava.restiloc.ui.adapter.MeetingItemDecoration


class PlanningFragment() : Fragment() {


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



        val apiService = ApiClient.apiService

        val planningList = ArrayList<Mission>()
        val reasonList = ArrayList<Reason>()

        // appel asynchrone de l'API
        lifecycleScope.launch {
            try {
                // recuperer le token de l'utilisateur
                sessionManager = SessionManager(requireContext())
                val missions = apiService.getInfos( "Bearer ${sessionManager.fetchAuthToken()}")
                val reasons = apiService.getReasons( "Bearer ${sessionManager.fetchAuthToken()}")

                Log.d("test", reasons.toString())
                for (mission in missions) {
                    planningList.add(mission)
                }
                for (reason in reasons) {
                    reasonList.add(reason)
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
