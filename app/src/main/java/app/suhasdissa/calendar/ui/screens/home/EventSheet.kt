package app.suhasdissa.calendar.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.NotificationAdd
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.ShareLocation
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.backend.models.Event
import app.suhasdissa.calendar.backend.viewmodels.EventViewModel
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun EventPage(
    onDismissRequest: () -> Unit,
    event: Event,
    eventViewModel: EventViewModel = viewModel(
        factory = EventViewModel.Factory
    )
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest, sheetState = sheetState) {
        val scroll = rememberScrollState()
        Column(Modifier.fillMaxSize().padding(8.dp).verticalScroll(scroll)) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(4 / 3f),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                val context = LocalContext.current
                FilledTonalIconButton(
                    onClick = { eventViewModel.openLocation(context, event.geoLocation) },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ShareLocation,
                        contentDescription = "Show Location"
                    )
                }
                FilledTonalIconButton(
                    onClick = { eventViewModel.addEvent(context, event) },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.NotificationAdd,
                        contentDescription = "Add Reminder"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Icon(imageVector = Icons.Rounded.Event, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        val date = remember {
                            SimpleDateFormat("dd MMM yyyy").format(Date(event.time))
                        }
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Card {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Icon(imageVector = Icons.Rounded.Schedule, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        val date = remember {
                            SimpleDateFormat("HH:mm a").format(Date(event.time))
                        }
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Icon(imageVector = Icons.Rounded.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = event.location,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Organized By",
                style = MaterialTheme.typography.titleLarge

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.organizer,
                style = MaterialTheme.typography.bodyLarge

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "About this event",
                style = MaterialTheme.typography.titleLarge

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyLarge

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contact",
                style = MaterialTheme.typography.titleLarge

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = event.contact,
                style = MaterialTheme.typography.bodyLarge

            )
        }
    }
}
