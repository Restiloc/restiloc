package space.ava.restiloc.classes

import com.google.gson.annotations.SerializedName

data class UpdateRequest(

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String,

)
