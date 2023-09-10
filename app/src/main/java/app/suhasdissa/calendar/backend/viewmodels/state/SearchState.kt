package app.suhasdissa.calendar.backend.viewmodels.state

import app.suhasdissa.calendar.backend.models.Event

sealed interface SearchState {
    object Empty : SearchState
    data class Success(val events: List<Event>) : SearchState
    object Error : SearchState
    object Loading : SearchState
    object NotStarted : SearchState
}
