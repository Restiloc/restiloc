package space.ava.restiloc.ui.statistiques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import space.ava.restiloc.databinding.FragmentStatistiquesBinding


class StatistiquesFragment : Fragment() {

    private var _binding: FragmentStatistiquesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val statistiquesViewModel =
            ViewModelProvider(this)[StatistiquesViewModel::class.java]

        _binding = FragmentStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textStatistiques
        statistiquesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}