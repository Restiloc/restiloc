package space.ava.restiloc.classes

data class Mission(
    val id: Int,
    val dateMission: String,
    val expert: Expert,
    val garage: Garage,
    val isFinished: Boolean,
    val kilometersCounter: Int,
    val nameExpertFile: String,
    val pree: List<Pree>,
    val route: String,
    val startedAt: Any,
    val unavailability: Unavailability,
    val vehicle: Vehicle
)