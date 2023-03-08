package space.ava.restiloc

import retrofit2.Call
import retrofit2.http.*
import space.ava.restiloc.classes.LoginRequest
import space.ava.restiloc.classes.LoginResponse
import space.ava.restiloc.classes.Mission

interface ApiInterface {
    @GET("api/me/missions")
    // recuperer un objet de type Data
    suspend fun getInfos(@Header("Authorization") token: String): List<Mission>

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}