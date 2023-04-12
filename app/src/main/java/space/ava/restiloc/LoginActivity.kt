package space.ava.restiloc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import space.ava.restiloc.classes.LoginRequest
import space.ava.restiloc.classes.LoginResponse

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        // Handle login button click
        loginButton.setOnClickListener {

            // recupere les données de connexion
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            val apiService = ApiClient.apiService

            apiService.login(LoginRequest( username = username, password = password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Error logging in
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()

                        if (loginResponse?.status == true) {
                            sessionManager.saveAuthToken(loginResponse.token)

                            sessionManager.setLogin(true)
                            // appeler la fonction login()
                            Toast.makeText(this@LoginActivity, "Bonjour", Toast.LENGTH_SHORT).show()
                            Log.d("LoginActivity", "Login successful: ${loginResponse.token}")
                            continuetoMainActivity()

                        } else {
                            // Error logging in
                            sessionManager.setLogin(false)
                            // mettre isLogin à false
                            Toast.makeText(this@LoginActivity, "Erreur de connexion", Toast.LENGTH_SHORT).show()
                            Log.d("LoginActivity", "Error logging in: ${loginResponse?.message}")
                        }
                    }
                })

        }
    }

    private fun continuetoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}