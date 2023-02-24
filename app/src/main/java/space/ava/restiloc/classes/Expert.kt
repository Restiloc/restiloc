package space.ava.restiloc.classes

data class Expert(
    val id: Int,
    val lastName: String,
    val firstName: String,
    val email: String,
    val phoneNumber: String,
    val username: String,
    val route: String
)