package space.ava.restiloc

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import space.ava.restiloc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Utilise le style approprié en fonction du mode actuel
        if (isDarkMode()) {
            setTheme(R.style.Theme_MyApplication_Dark)
        } else {
            setTheme(R.style.Theme_MyApplication)
        }

        // Initialize SessionManager
        val sessionManager = SessionManager(this)

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {

            // Navigate to LandingActivity if user is not logged in
            val intent = Intent(this, LandingActivity::class.java)
           startActivity(intent)
           finish()
          return
        }
        else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.



            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_planning, R.id.navigation_statistiques, R.id.navigation_settings
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            // Définissez votre ColorStateList
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(
                    ContextCompat.getColor(this, R.color.blue_primary), // couleur pour les boutons actifs
                    ContextCompat.getColor(this, R.color.white_secondary) // couleur pour les boutons inactifs
                )
            )

// Appliquez la ColorStateList à votre barre de navigation
            navView.itemTextColor = colorStateList
            navView.itemIconTintList = colorStateList
        }

    }

    private fun isDarkMode(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}