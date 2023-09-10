package app.suhasdissa.calendar.backend.viewmodels.state

sealed interface AddEventState {
    object NoJob : AddEventState
    object Uploading : AddEventState
    object Success : AddEventState
    object Failed : AddEventState
}
