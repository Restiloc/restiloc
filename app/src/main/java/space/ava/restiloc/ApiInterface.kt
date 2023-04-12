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

    @GET("api/me")
    fun getCurrentExpert(@Header("Authorization") token: String): Call<Expert>

    @PUT("api/experts/{id}")
    fun updateExpert(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateRequest: UpdateRequest
    ): Call<UpdateResponse>

    @Headers("Content-Type: application/json")
    @GET("api/reasons")
    suspend fun getReasons(@Header("Authorization") token: String): List<Reason>

    @Headers("Content-Type: application/json")
    @POST("api/unavailabilities")
    fun postUnavailability(@Header("Authorization") token: String, @Body request: Unavailability): Call<UnavailabilityResponse>
}