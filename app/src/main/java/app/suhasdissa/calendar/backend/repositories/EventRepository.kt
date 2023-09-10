package app.suhasdissa.calendar.backend.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import app.suhasdissa.calendar.backend.api.AddEventApi
import app.suhasdissa.calendar.backend.api.EventApi
import app.suhasdissa.calendar.backend.models.Event
import app.suhasdissa.calendar.backend.utils.InputStreamRequestBody
import app.suhasdissa.calendar.backend.utils.getExt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody

interface EventRepository {
    suspend fun getEvents(query: String? = null, category: String? = null): List<Event>
    suspend fun getEvent(id: Int): Event
    suspend fun getSearchSuggestions(query: String): List<String>

    suspend fun addEvent(context: Context, event: Event, fileUri: Uri): Boolean
}

class EventRepositoryImpl(private val eventApi: EventApi, private val addEventApi: AddEventApi) :
    EventRepository {
    override suspend fun getEvents(query: String?, category: String?): List<Event> =
        eventApi.getEvents(query, category)

    override suspend fun getEvent(id: Int) = eventApi.getEvent(id.toString())
    override suspend fun getSearchSuggestions(query: String) = eventApi.getSearchSuggestions(query)

    override suspend fun addEvent(context: Context, event: Event, fileUri: Uri): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val imagePart = MultipartBody.Part.createFormData(
                    "image",
                    "upload.${fileUri.getExt(context)}",
                    InputStreamRequestBody(
                        "image/*".toMediaType(),
                        context.contentResolver,
                        fileUri
                    )
                )
                val response = addEventApi.addNewEvent(
                    event.name,
                    imagePart,
                    event.time.toString(),
                    event.description,
                    event.category,
                    event.organizer,
                    event.location,
                    event.contact
                )
                Log.d("Server Response", response)
                true
            } catch (e: Exception) {
                Log.e("New Event Post", e.message, e)
                false
            }
        }
    }
}
