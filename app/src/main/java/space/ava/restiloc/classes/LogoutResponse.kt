package space.ava.restiloc.classes

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

    @SerializedName("status")
    var status: Boolean,

    @SerializedName("message")
    var message: String

)
