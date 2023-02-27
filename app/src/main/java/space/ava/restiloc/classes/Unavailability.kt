package space.ava.restiloc.classes

data class Unavailability(
    val id: Int,
    val customerResponsible: Boolean,
    val route: String
)