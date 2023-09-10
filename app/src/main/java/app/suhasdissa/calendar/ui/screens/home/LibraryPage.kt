package app.suhasdissa.calendar.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.suhasdissa.calendar.Destination
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.ui.components.IllustratedMessageScreen

@Composable
fun LibraryPage(onNavigate: (Destination) -> Unit) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            onNavigate(Destination.AddEvent)
        }) {
            Text("Add New Event")
        }
    }) { pv ->
        Column(Modifier.fillMaxSize().padding(pv)) {
            IllustratedMessageScreen(
                image = R.drawable.ic_launcher_monochrome,
                message = R.string.no_saved_events
            )
        }
    }
}
