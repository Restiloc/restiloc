package space.ava.restiloc

import retrofit2.http.GET
import retrofit2.http.Header
import space.ava.restiloc.classes.Mission

interface ApiInterface {
    @GET("api/me/missions")
    // recuperer un objet de type Data
    suspend fun getInfos(@Header("Authorization") token: String): List<Mission>
}