package app.suhasdissa.calendar.backend.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.suhasdissa.calendar.EventCalendarApplication
import app.suhasdissa.calendar.backend.models.Event
import app.suhasdissa.calendar.backend.models.categories
import app.suhasdissa.calendar.backend.repositories.EventRepository
import app.suhasdissa.calendar.backend.viewmodels.state.AddEventState
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var selectedCategory by mutableStateOf(categories.first())
    var selectedImageUrl by mutableStateOf<Uri?>(null)

    var addEventState by mutableStateOf<AddEventState>(AddEventState.NoJob)

    var name by mutableStateOf("")
    var organizer by mutableStateOf("")
    var description by mutableStateOf("")
    var contact by mutableStateOf("")
    var location by mutableStateOf("")

    val datePickerState: DatePickerState
    val timePickerState: TimePickerState

    init {
        val currentTime = System.currentTimeMillis()
        val cal: Calendar = Calendar.getInstance()
        cal.time = Date(currentTime)
        val hours: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)

        timePickerState = TimePickerState(
            initialHour = hours,
            initialMinute = minutes,
            is24Hour = false
        )

        datePickerState = DatePickerState(
            initialSelectedDateMillis = currentTime,
            initialDisplayedMonthMillis = currentTime,
            yearRange = IntRange(2023, 2025),
            initialDisplayMode = DisplayMode.Picker
        )
    }

    fun addEvent(context: Context) {
        if (selectedImageUrl == null) return
        if (name.isBlank()) return
        if (organizer.isBlank()) return
        if (description.isBlank()) return
        if (contact.isBlank()) return
        if (location.isBlank()) return

        val cal = Calendar.getInstance().apply {
            timeInMillis = datePickerState.selectedDateMillis ?: 0L
            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
            set(Calendar.MINUTE, timePickerState.minute)
        }

        val event = Event(
            id = 0,
            image = "",
            name = name,
            organizer = organizer,
            description = description,
            contact = contact,
            location = location,
            category = selectedCategory.id,
            time = cal.time.time
        )
        viewModelScope.launch {
            addEventState = AddEventState.Uploading
            val result = eventRepository.addEvent(context, event, selectedImageUrl!!)
            addEventState = if (result) AddEventState.Success else AddEventState.Failed
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventCalendarApplication
                AddEventViewModel(application.container.eventRepository)
            }
        }
    }
}
