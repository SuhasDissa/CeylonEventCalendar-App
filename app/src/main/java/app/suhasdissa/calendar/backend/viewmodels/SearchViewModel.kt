package app.suhasdissa.calendar.backend.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.suhasdissa.calendar.EventCalendarApplication
import app.suhasdissa.calendar.backend.repositories.EventRepository
import app.suhasdissa.calendar.backend.viewmodels.state.SearchState
import kotlinx.coroutines.launch

class SearchViewModel(private val eventRepository: EventRepository) : ViewModel() {

    var suggestions by mutableStateOf<List<String>>(emptyList())
    var search by mutableStateOf("")
    var searchState: SearchState by mutableStateOf(SearchState.NotStarted)

    fun search() {
        viewModelScope.launch {
            searchState = SearchState.Loading
            searchState = try {
                val events = eventRepository.getEvents(search)
                if (events.isNotEmpty()) {
                    SearchState.Success(events)
                } else {
                    SearchState.Empty
                }
            } catch (e: Exception) {
                SearchState.Error
            }
        }
    }

    fun getSuggestions() {
        if (search.length < 3) return
        val insertedTextTemp = search
        Handler(
            Looper.getMainLooper()
        ).postDelayed(
            {
                if (insertedTextTemp == search) {
                    viewModelScope.launch {
                        runCatching {
                            suggestions = eventRepository.getSearchSuggestions(search).take(6)
                        }
                    }
                }
            },
            500L
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventCalendarApplication
                SearchViewModel(application.container.eventRepository)
            }
        }
    }
}
