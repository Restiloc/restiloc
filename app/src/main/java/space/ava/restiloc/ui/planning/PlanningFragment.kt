package space.ava.restiloc.ui.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import space.ava.restiloc.R
import space.ava.restiloc.databinding.FragmentPlanningBinding
import space.ava.restiloc.ui.adapter.MeetingAdapter
import space.ava.restiloc.ui.adapter.MeetingItemDecoration


class PlanningFragment : Fragment() {

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

        val verticalRecyclerView = root?.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView?.adapter = MeetingAdapter(R.layout.item_horizontal)
        verticalRecyclerView?.addItemDecoration(MeetingItemDecoration())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


