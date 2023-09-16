package app.suhasdissa.calendar.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.calendar.Destination
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.ui.screens.home.EventsPage
import app.suhasdissa.calendar.ui.screens.home.ExplorePage
import app.suhasdissa.calendar.ui.screens.home.LibraryPage

@Composable
fun HomeScreen(onNavigate: (Destination) -> Unit) {
    var page by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { onNavigate(Destination.Search) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_monochrome),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Search for Events",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        IconButton(onClick = { onNavigate(Destination.Settings) }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(
                                    R.string.settings
                                ),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = (page == 0), onClick = {
                    page = 0
                }, icon = {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = null
                    )
                }, label = {
                    Text("Home")
                })
                NavigationBarItem(selected = (page == 1), onClick = {
                    page = 1
                }, icon = {
                    Icon(
                        imageVector = Icons.Rounded.Explore,
                        contentDescription = null
                    )
                }, label = {
                    Text("Explore")
                })
                NavigationBarItem(selected = (page == 2), onClick = {
                    page = 2
                }, icon = {
                    Icon(
                        imageVector = Icons.Rounded.LibraryBooks,
                        contentDescription = null
                    )
                }, label = {
                    Text("Library")
                })
            }
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Crossfade(targetState = page, label = "Home page crossfade") {
                when (it) {
                    0 -> EventsPage()
                    1 -> ExplorePage(onNavigate)
                    2 -> LibraryPage(onNavigate)
                }
            }
        }
    }
}
