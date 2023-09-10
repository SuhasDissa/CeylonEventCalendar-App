package app.suhasdissa.calendar.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.backend.viewmodels.CategoryViewModel
import app.suhasdissa.calendar.backend.viewmodels.state.SearchState
import app.suhasdissa.calendar.ui.components.EventsList
import app.suhasdissa.calendar.ui.components.IllustratedErrorScreen
import app.suhasdissa.calendar.ui.components.IllustratedMessageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = viewModel(
        LocalContext.current as ComponentActivity,
        factory = CategoryViewModel.Factory
    )
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                categoryViewModel.currentCategory?.let {
                    Icon(imageVector = it.icon, contentDescription = null)
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = stringResource(id = it.name)
                    )
                }
            }
        })
    }) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(8.dp)
        ) {
            when (val state = categoryViewModel.searchState) {
                is SearchState.Success -> EventsList(events = state.events)

                SearchState.Error -> IllustratedErrorScreen(
                    image = R.drawable.calendar_error,
                    R.string.something_went_wrong
                )

                SearchState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                SearchState.NotStarted -> IllustratedMessageScreen(
                    image = R.drawable.ic_launcher_monochrome,
                    message = R.string.search_for_events
                )

                SearchState.Empty -> IllustratedMessageScreen(
                    image = R.drawable.ic_launcher_monochrome,
                    message = R.string.no_events_found
                )
            }
        }
    }
}
