package space.ava.restiloc

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        const val USER_TOKEN = "user_token"
    }

    // Save user token
    fun saveAuthToken(token: String) {
        editor.putString(USER_TOKEN, token)
        editor.apply()
        Log.d(token , "token saved")
    }

// Fetch user token

    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)

    }


    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun setLogin(isLoggedIn: Boolean = false )  {
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}