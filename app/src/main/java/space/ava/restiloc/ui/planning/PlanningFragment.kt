package space.ava.restiloc.ui.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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

       // val textView: TextView = binding.textPlanning
       // planningViewModel.text.observe(viewLifecycleOwner) {
       //     textView.text = it
       // }

        // créer une liste qui va stocker ces elements
        val planningList = arrayListOf<Mission>()

        // enregistrer une premiere voiture


        val expert1 = Expert(
            3, "Jean", "Valjean", "PetitJean", "petitjean@hotmail.fr", "061815778", "https://restiloc.fr"
        )

        val garage1 = Garage(
            1, "LeGarage", "6 ", "Strasbourg", "155512", "5456456", "0618484848", "67522", "rue des pieds", "https://restiloc.fr"
        )

        val preeList = arrayListOf<Pree>()
        preeList.add(Pree(
            1, "Je sais pas ce que c'est", "C'est une super description", "https://restiloc.space", "https://restiloc.space", "Alizée"
        )
        )
        preeList.add(Pree(
            2, "Je sais pas ce que c'est", "C'est une super description", "https://restiloc.space", "https://restiloc.space", "Alizée"
        )
        )

        val unavailability1 = Unavailability(
            1, 2, "restiloc.space"
        )

        val vehicle1 = Vehicle(
            1, "rouge", "AHDUDS", 2021, "https://restiloc.space"
        )

        planningList.add(
            Mission(
                1, "10-12-23", expert1, garage1, true, 15500,"XLFEHDS", preeList, "https://restiloc.space", "10-02-23", unavailability1 , vehicle1)
        )

        planningList.add(
            Mission(
                2, "10-12-23", expert1, garage1, true, 15500,"XLFEHDS", preeList, "https://restiloc.space", "10-02-23", unavailability1 , vehicle1)
        )

        planningList.add(
            Mission(
                3, "10-12-23", expert1, garage1, true, 15500,"XLFEHDS", preeList, "https://restiloc.space", "10-02-23", unavailability1 , vehicle1)
        )

        planningList.add(
            Mission(
                4, "10-12-23", expert1, garage1, true, 15500,"XLFEHDS", preeList, "https://restiloc.space", "10-02-23", unavailability1 , vehicle1)
        )


        val verticalRecyclerView = root?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = MeetingAdapter(planningList, R.layout.item_horizontal)
        verticalRecyclerView?.addItemDecoration(MeetingItemDecoration())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


