package space.ava.restiloc

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import space.ava.restiloc.classes.*

interface ApiInterface {
    @GET("api/me/missions")
    // recuperer un objet de type Data
    suspend fun getInfos(@Header("Authorization") token: String): List<Mission>

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @Headers("Content-Type: application/json")
    @GET("api/reasons")
    suspend fun getReasons(@Header("Authorization") token: String): List<Reason>

    @Headers("Content-Type: application/json")
    @POST("api/unavailabilities")
    fun postUnavailability(@Header("Authorization") token: String, @Body request: Unavailability): Response<Unavailability>
}