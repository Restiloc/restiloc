package space.ava.restiloc.classes

data class Mission(
    val id: Int,
    val dateMission: String,
    val startedAt: String,
    val kilometersCounter: Int,
    val folder: String,
    val type: String,
    val isFinished: Boolean,
    val route: String,
    val vehicle: Vehicle,
    val expert: Expert,
    val garage: Garage,
    val client: Client,
    val unavailability: Unavailability,
    val pree: List<Pree>,
)