package app.suhasdissa.calendar.backend.viewmodels.state

import app.suhasdissa.calendar.backend.models.Event

sealed interface EventsState {
    object Loading : EventsState
    object Error : EventsState
    data class Success(val events: List<Event>) : EventsState
}
