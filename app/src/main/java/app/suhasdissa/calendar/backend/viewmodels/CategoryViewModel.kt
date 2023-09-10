package app.suhasdissa.calendar.backend.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.suhasdissa.calendar.EventCalendarApplication
import app.suhasdissa.calendar.backend.models.Category
import app.suhasdissa.calendar.backend.repositories.EventRepository
import app.suhasdissa.calendar.backend.viewmodels.state.SearchState
import kotlinx.coroutines.launch

class CategoryViewModel(private val eventRepository: EventRepository) : ViewModel() {

    var searchState: SearchState by mutableStateOf(SearchState.NotStarted)
    var currentCategory by mutableStateOf<Category?>(null)
    fun searchByCategory(category: Category) {
        currentCategory = category
        viewModelScope.launch {
            searchState = SearchState.Loading
            searchState = try {
                val events = eventRepository.getEvents(null, category.id)
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

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventCalendarApplication
                CategoryViewModel(application.container.eventRepository)
            }
        }
    }
}
