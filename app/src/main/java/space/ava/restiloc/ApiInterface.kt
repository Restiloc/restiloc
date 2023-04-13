package space.ava.restiloc

import retrofit2.Call
import retrofit2.Response
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

    // les information de l'expert
    @GET("api/me")
    fun getCurrentExpert(@Header("Authorization") token: String): Call<Expert>

    @PUT("api/experts/{id}")
    fun updateExpert(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateRequest: UpdateRequest
    ): Call<UpdateResponse>

    // récupération de la route des reasons
    @Headers("Content-Type: application/json")
    @GET("api/reasons")
    suspend fun getReasons(@Header("Authorization") token: String): List<Reason>

    // récupération de la route des unavailabilities
    @Headers("Content-Type: application/json")
    @POST("api/unavailabilities")
    fun postUnavailability(@Header("Authorization") token: String, @Body request: Unavailability): Call<UnavailabilityResponse>

    @GET("api/stats")
    // récupération de la route des statistiques
    suspend fun getStats(@Header("Authorization") token: String): List<Stats>

    @GET("api/stats")
    // récupération de la route des statistiques
    suspend fun getStatsBetweenDates(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String): List<Stats>

}