package space.ava.restiloc.classes

data class Garage(
    val id: Int,
    val name: String,
    val addressNumber: String,
    val street: String,
    val postalCode: String,
    val city: String,
    val phoneNumber: String,
    val latitude: String,
    val longitude: String,
    val url: String
)