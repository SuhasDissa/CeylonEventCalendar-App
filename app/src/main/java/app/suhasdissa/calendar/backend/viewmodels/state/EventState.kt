package app.suhasdissa.calendar.backend.viewmodels.state

import app.suhasdissa.calendar.backend.models.Event

sealed interface EventState {
    object Loading : EventState
    object Error : EventState
    data class Success(val events: Event) : EventState
}
