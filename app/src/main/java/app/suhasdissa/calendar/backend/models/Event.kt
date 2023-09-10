package app.suhasdissa.calendar.backend.models

import app.suhasdissa.calendar.BASE_URL
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int,
    val image: String,
    val name: String,
    val time: Long,
    val description: String,
    val category: String,
    val organizer: String,
    val location: String,
    val contact: String
) {
    val imageUrl = BASE_URL + "uploads/$image"
}
