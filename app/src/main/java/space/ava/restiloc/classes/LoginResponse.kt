package space.ava.restiloc.classes

import com.google.gson.annotations.SerializedName

data class LoginResponse(

        @SerializedName("status")
        var status: Boolean,

        @SerializedName("message")
        var message: String,

        @SerializedName("token")
        var token: String,

)
