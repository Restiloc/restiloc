package space.ava.restiloc.classes

import com.google.gson.annotations.SerializedName

data class LoginRequest(

@SerializedName("identifier")
var username: String,

@SerializedName("password")
var password: String

)
