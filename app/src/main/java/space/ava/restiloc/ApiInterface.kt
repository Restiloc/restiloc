package space.ava.restiloc

import retrofit2.Call
import retrofit2.http.*
import space.ava.restiloc.classes.*

interface ApiInterface {
    @GET("api/me/missions")
    // récupération d'un objet de type Data
    suspend fun getInfos(@Header("Authorization") token: String): List<Mission>

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @GET("api/stats")
    // récupération de la route des statistiques
    suspend fun getStats(@Header("Authorization") token: String): List<Stats>

    @GET("api/stats?startDate={startDate}&endDate={endDate}")
    // récupération de la route des statistiques
    suspend fun getStatsBetweenDates(@Header("Authorization") token: String, startDate : String, endDate : String): List<Stats>

}