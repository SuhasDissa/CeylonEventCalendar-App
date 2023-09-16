package app.suhasdissa.calendar.backend.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.suhasdissa.calendar.EventCalendarApplication
import app.suhasdissa.calendar.backend.models.Event
import app.suhasdissa.calendar.backend.repositories.EventRepository
import app.suhasdissa.calendar.backend.viewmodels.state.EventsState
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

class EventViewModel(eventRepository: EventRepository) : ViewModel() {

    var eventsState: EventsState by mutableStateOf(EventsState.Loading)

    init {
        viewModelScope.launch {
            eventsState = EventsState.Loading

            eventsState = try {
                EventsState.Success(eventRepository.getEvents())
            } catch (e: Exception) {
                EventsState.Error
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun addEvent(context: Context, event: Event) {
        val cal: Calendar = Calendar.getInstance()
        cal.time = Date(event.time)
        val intent = Intent(Intent.ACTION_EDIT).apply {
            type = "vnd.android.cursor.item/event"
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.timeInMillis)
            putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
            putExtra(CalendarContract.Events.TITLE, event.name)
            putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun openLocation(context: Context, geoLocation: Uri?) {
        if (geoLocation == null) return
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        context.startActivity(intent)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as EventCalendarApplication
                EventViewModel(application.container.eventRepository)
            }
        }
    }
}
