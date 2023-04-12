package space.ava.restiloc

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object ApiClient {
    public const val BASE_URL = "https://restiloc.space/"
     //public const val LOCAL_URL = "http://127.0.0.1:8000/"


    private val gson : Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        //.baseUrl(LOCAL_URL)
            // convertir en objet json
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
        .build()

    val apiService = retrofit.create(ApiInterface::class.java)
}