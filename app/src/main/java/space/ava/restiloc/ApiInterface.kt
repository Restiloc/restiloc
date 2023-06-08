package space.ava.restiloc

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import space.ava.restiloc.classes.*

interface ApiInterface {

    @POST("api/pree/{id}")
    fun uploadPhoto(@Header("Authorization") token: String, @Path("id") id: RequestBody, @Body request: PreePost): Call<ApiResponse>

    @GET("api/me/missions?p=today")
    // récupération d'un objet de type Data
    suspend fun getInfos(@Header("Authorization") token: String): List<Mission>?

    @GET("api/me/missions")
    // récupération d'un objet de type Data
    suspend fun getNextMission(@Header("Authorization") token: String): List<Mission>?

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<ApiResponse>

    // les information de l'expert
    @GET("api/me")
    fun getCurrentExpert(@Header("Authorization") token: String): Call<Expert>

    @PUT("api/experts/{id}")
    fun updateExpert(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateRequest: UpdateRequest
    ): Call<ApiResponse>

    // récupération de la route des reasons
    @Headers("Content-Type: application/json")
    @GET("api/reasons")
    suspend fun getReasons(@Header("Authorization") token: String): List<Reason>

    // récupération de la route des unavailabilities
    @Headers("Content-Type: application/json")
    @POST("api/unavailabilities")
    fun postUnavailability(@Header("Authorization") token: String, @Body request: Unavailability): Call<ApiResponse>

    @GET("api/stats")
    // récupération de la route des statistiques
    suspend fun getStats(@Header("Authorization") token: String): List<Stats>

    @GET("api/stats")
    // récupération de la route des statistiques
    suspend fun getStatsBetweenDates(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String): List<Stats>


    @POST("api/pree")
    fun addExpertise(@Header("Authorization") token: String, @Body request: PreePost): Call<ApiResponse>


    @Headers("Content-Type: application/json")
    @PUT("api/missions/{id}")
    fun closeMission(@Header("Authorization") token: String, @Path("id") id: String, @Body request: MissionRequest): Call<ApiResponse>
    abstract fun uploadPhoto(token: String, id: RequestBody): Call<ApiResponse>
}


