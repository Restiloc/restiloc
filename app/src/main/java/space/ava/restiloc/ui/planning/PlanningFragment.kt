package space.ava.restiloc.ui.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import space.ava.restiloc.databinding.FragmentPlanningBinding


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

        val textView: TextView = binding.textPlanning
        planningViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


