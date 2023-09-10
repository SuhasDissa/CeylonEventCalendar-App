package app.suhasdissa.calendar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.backend.viewmodels.SearchViewModel
import app.suhasdissa.calendar.backend.viewmodels.state.SearchState
import app.suhasdissa.calendar.ui.components.EventsList
import app.suhasdissa.calendar.ui.components.IllustratedErrorScreen
import app.suhasdissa.calendar.ui.components.IllustratedMessageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
) {
    Column(Modifier.fillMaxSize()) {
        var active by remember {
            mutableStateOf(searchViewModel.searchState == SearchState.NotStarted)
        }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchViewModel.search,
            onQueryChange = {
                searchViewModel.search = it
                searchViewModel.getSuggestions()
            },
            onSearch = {
                active = false
                searchViewModel.search()
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = "Search for Events"
                )
            }
        ) {
            val scroll = rememberScrollState()
            Column(Modifier.verticalScroll(scroll).padding(horizontal = 8.dp)) {
                searchViewModel.suggestions.forEach {
                    ListItem(
                        modifier = Modifier.clickable {
                            searchViewModel.search = it
                            searchViewModel.search()
                            active = false
                        },
                        headlineContent = { Text(it) },
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (val state = searchViewModel.searchState) {
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
