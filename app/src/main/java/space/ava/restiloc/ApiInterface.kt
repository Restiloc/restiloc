package space.ava.restiloc

import retrofit2.http.GET
import space.ava.restiloc.classes.Mission

interface ApiInterface {
    @GET("api/infos")
    // recuperer un objet de type Data
    suspend fun getInfos(): List<Mission>
}