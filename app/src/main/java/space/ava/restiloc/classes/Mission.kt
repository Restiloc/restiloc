package space.ava.restiloc.classes

data class Mission(
    val id: Int,
    val dateMission: String,
    val startedAt: Any,
    val kilometersCounter: Int,
    val nameExpertFile: String,
    val isFinished: Int,
    val route: String,
    val vehicle: Vehicle,
    val expert: Expert,
    val garage: Garage,
    val unavailability: Unavailability,
    val pree: List<Pree>,
)