package space.ava.restiloc.classes

import com.google.gson.annotations.SerializedName

data class UpdateResponse(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("message")
    var message: String,

    )
