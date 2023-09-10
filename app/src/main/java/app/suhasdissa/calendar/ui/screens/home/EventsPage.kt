package app.suhasdissa.calendar.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.backend.viewmodels.EventViewModel
import app.suhasdissa.calendar.backend.viewmodels.state.EventsState
import app.suhasdissa.calendar.ui.components.EventsList
import app.suhasdissa.calendar.ui.components.IllustratedErrorScreen

@Composable
fun EventsPage(
    viewModel: EventViewModel = viewModel(
        factory = EventViewModel.Factory
    )
) {
    when (val state = viewModel.eventsState) {
        is EventsState.Success -> EventsList(events = state.events)

        EventsState.Error -> IllustratedErrorScreen(
            image = R.drawable.calendar_error,
            R.string.couldn_t_connect_to_the_server
        )

        EventsState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
