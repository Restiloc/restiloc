package space.ava.restiloc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        val loginButton = findViewById<Button>(R.id.loginButton)
        // Handle login button click
        loginButton.setOnClickListener {
            // Perform login operation
            // ...

            // Set login state in SharedPreferences
            sessionManager.setLogin(true)

            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}