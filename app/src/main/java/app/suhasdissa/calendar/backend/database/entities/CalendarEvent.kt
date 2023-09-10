package app.suhasdissa.calendar.backend.database.entities

data class CalendarEvent(
    val id: Int,
    val image: String,
    val name: String,
    val time: Long,
    val description: String,
    val category: String,
    val organizer: String,
    val location: String,
    val contact: String
)
