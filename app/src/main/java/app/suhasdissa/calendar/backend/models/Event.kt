package app.suhasdissa.calendar.backend.models

import android.net.Uri
import androidx.core.net.toUri
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
    val latitude: Double? = 6.9969277,
    val longitude: Double? = 81.0527293,
    val contact: String
) {
    val imageUrl = BASE_URL + "uploads/$image"
    val geoLocation: Uri?
        get() = if (latitude != null && longitude != null) {
            "geo:$latitude,$longitude".toUri()
        } else {
            null
        }
}
