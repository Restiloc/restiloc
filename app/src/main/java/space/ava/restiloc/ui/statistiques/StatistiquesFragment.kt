package space.ava.restiloc.ui.statistiques

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.ApiInterface
import space.ava.restiloc.R
import space.ava.restiloc.SessionManager
import space.ava.restiloc.classes.Stats
import space.ava.restiloc.databinding.FragmentStatistiquesBinding
import space.ava.restiloc.ui.adapter.StatsAdapter
import space.ava.restiloc.ui.adapter.StatsItemDecoration
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class StatistiquesFragment() : Fragment(), DatePickerDialog.OnDateSetListener {

    // Variables pour la date
    private var datePickerSelected : Int = 0

    // Variables pour la première date
    var firstDay = 0
    var firstMonth = 0
    var firstYear = 0

    var saveFirstDay = 0
    var saveFirstMonth = 0
    var saveFirstYear = 0

    var firstCompletedDate : String = ""

    // Variables pour la deuxième date
    var secondDay = 0
    var secondMonth = 0
    var secondYear = 0

    var saveSecondDay = 0
    var saveSecondMonth = 0
    var saveSecondYear = 0

    var secondCompletedDate : String = ""

    // Cherche les dates dans le layout
    private lateinit var firstDate : TextView
    private lateinit var firstBtn : Button

    private lateinit var secondDate : TextView
    private lateinit var secondBtn : Button

    private var _binding: FragmentStatistiquesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            ViewModelProvider(this)[StatistiquesViewModel::class.java]

        _binding = FragmentStatistiquesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Appel à la session
        lateinit var sessionManager: SessionManager

        // recuperer les données de l'API

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restiloc.space/")
            //.baseUrl("http://127.0.0.1:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)
        val statsList = ArrayList<Stats>()

        // appel asynchrone de l'API
         lifecycleScope.launch {
            try {
                // recuperer le token de l'utilisateur
                sessionManager = SessionManager(requireContext())
                // recuperer les infos dans getStats()
               val stats = apiService.getStats( "Bearer ${sessionManager.fetchAuthToken()}")



                for (stat in stats) {
                    statsList.add(stat)
                }

                Log.d("statsList", statsList[0].reason.toString())


                // Recuperation du Recycler view
                val verticalRecyclerView = root.findViewById<RecyclerView>(R.id.vertical_recycler_view_stats)
                verticalRecyclerView?.adapter = StatsAdapter(statsList, R.layout.item_horizontal_stats)
                verticalRecyclerView?.addItemDecoration(StatsItemDecoration())
                Log.d("test", "test")

                // Si je suis en erreur
            } catch (e: Exception) {
                // PrintStackTrace pour afficher l'erreur
                e.printStackTrace()
                // verifie dans quel condition je suis
                Log.d("test", "testError")
            }

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ajout de la méthode pickDate() ici après la création de la vue
        pickDateFirst()

        pickDateSecond()

              // Récupération du bouton "Get stats"
              val getStatsButton = view.findViewById<Button>(R.id.getStatsButton)


              // Ajout d'un listener pour écouter les clics
              getStatsButton.setOnClickListener {
                  // Appeler la méthode pour récupérer les statistiques
                  getStats()
              }

    }

    private fun getStats() {
        // Vérifier si les deux dates ont été sélectionnées
        if (saveFirstDay == 0 || saveSecondDay == 0) {
            Toast.makeText(requireContext(), "Veuillez sélectionner deux dates", Toast.LENGTH_SHORT).show()
            return
        }

        // Appel à la session
        lateinit var sessionManager: SessionManager

        // recuperer les données de l'API

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restiloc.space/")
            //.baseUrl("http://127.0.0.1:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiInterface::class.java)
        val statsList = ArrayList<Stats>()

        // appel asynchrone de l'API
        lifecycleScope.launch {
            // recuperer le token de l'utilisateur
            sessionManager = SessionManager(requireContext())
            // recuperer les infos dans getStats()
            val stats = apiService.getStatsBetweenDates(
                "Bearer ${sessionManager.fetchAuthToken()}",
                firstCompletedDate,
                secondCompletedDate
            )
        Log.d("stats", stats.toString())
        }
    }


    private fun getDateTimeCalendarFirst() {
        val cal : Calendar = Calendar.getInstance()
        firstYear = cal.get(Calendar.YEAR)
        firstMonth = cal.get(Calendar.MONTH)
        firstDay = cal.get(Calendar.DAY_OF_MONTH)

        val date = cal.time
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        firstCompletedDate = formatter.format(date)
    }


    private fun pickDateFirst() {
        // Find the datePickerButton in the layout
        firstBtn = requireView().findViewById(R.id.firstDatePickerButton)


        firstBtn.setOnClickListener {
            datePickerSelected = 1
            getDateTimeCalendarFirst()
            DatePickerDialog(requireContext(), this, firstYear, firstMonth, firstDay).show()
        }

    }

    private fun getDateTimeCalendarSecond() {
        val cal : Calendar = Calendar.getInstance()
        secondYear = cal.get(Calendar.YEAR)
        secondMonth = cal.get(Calendar.MONTH)
        secondDay = cal.get(Calendar.DAY_OF_MONTH)

        val date = cal.time
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        secondCompletedDate = formatter.format(date)
    }

    private fun pickDateSecond() {
        // Find the datePickerButton in the layout

        secondBtn = requireView().findViewById(R.id.secondDatePickerButton)

        secondBtn.setOnClickListener {
            datePickerSelected = 2
            getDateTimeCalendarSecond()
            DatePickerDialog(requireContext(), this, secondYear, secondMonth, secondDay).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Find the text for the first date in the layout
        if (datePickerSelected == 1) {
            firstDate = requireView().findViewById(R.id.dateOne)

            saveFirstDay = dayOfMonth
            saveFirstMonth = month+1
            saveFirstYear = year

            getDateTimeCalendarFirst()

            firstDate.text = "$saveFirstDay/$saveFirstMonth/$saveFirstYear"

            Log.d("pickDateFirst()", view.toString())
        } else if (datePickerSelected == 2) {
            secondDate = requireView().findViewById(R.id.dateTwo)

            saveSecondDay = dayOfMonth
            saveSecondMonth = month+1
            saveSecondYear = year

            getDateTimeCalendarSecond()

            secondDate.text = "$saveSecondDay/$saveSecondMonth/$saveSecondYear"
        }
    }

 }
