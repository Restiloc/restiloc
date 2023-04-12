package space.ava.restiloc.classes

data class Unavailability(
    val customerResponsible: Boolean,
    val reason_id: Int,
    val mission_id: Int,

)