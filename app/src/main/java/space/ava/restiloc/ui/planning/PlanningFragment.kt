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
import space.ava.restiloc.ApiInterface
import space.ava.restiloc.R
import space.ava.restiloc.classes.*
import space.ava.restiloc.databinding.FragmentPlanningBinding
import space.ava.restiloc.ui.adapter.MeetingAdapter
import space.ava.restiloc.ui.adapter.MeetingItemDecoration


class PlanningFragment(
) : Fragment() {

    private var _binding: FragmentPlanningBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val planningViewModel =
            ViewModelProvider(this)[PlanningViewModel::class.java]
        _binding = FragmentPlanningBinding.inflate(inflater, container, false)
        val root: View = binding.root



        // recuperer les données de l'API

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restiloc.space/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)
        val planningList = ArrayList<Mission>()

        // appel asynchrone de l'API
        lifecycleScope.launch {
            try {
                val missions = apiService.getInfos()
                for (mission in missions) {
                    planningList.add(mission)
                }
                val verticalRecyclerView = root?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
                verticalRecyclerView?.adapter = MeetingAdapter(planningList, R.layout.item_horizontal)
                verticalRecyclerView?.addItemDecoration(MeetingItemDecoration())
                Log.d("test", "test")

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


