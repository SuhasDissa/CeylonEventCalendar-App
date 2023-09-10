package app.suhasdissa.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.suhasdissa.calendar.backend.models.Event
import app.suhasdissa.calendar.ui.screens.home.EventPage

@Composable
fun EventsList(events: List<Event>) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(350.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = events) {
            EventCard(event = it) {
                selectedEvent = it
            }
        }
    }

    selectedEvent?.let {
        EventPage(onDismissRequest = { selectedEvent = null }, event = it)
    }
}
