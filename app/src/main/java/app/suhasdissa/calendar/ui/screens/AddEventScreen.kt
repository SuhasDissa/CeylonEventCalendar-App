package app.suhasdissa.calendar.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.R
import app.suhasdissa.calendar.backend.models.Category
import app.suhasdissa.calendar.backend.models.categories
import app.suhasdissa.calendar.backend.viewmodels.AddEventViewModel
import app.suhasdissa.calendar.backend.viewmodels.state.AddEventState
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun AddEventScreen(vm: AddEventViewModel = viewModel(factory = AddEventViewModel.Factory)) {
    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCategory by remember { mutableStateOf(false) }

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        if (imageUri != null) {
            vm.selectedImageUrl = imageUri
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Add New Event") })
    }, floatingActionButton = {
        val context = LocalContext.current
        FloatingActionButton(onClick = { vm.addEvent(context) }) {
            Icon(imageVector = Icons.Rounded.Save, contentDescription = "Save")
        }
    }) { pv ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(modifier = Modifier.fillMaxWidth(), value = vm.name, onValueChange = {
                vm.name = it
            }, placeholder = { Text("Name") }, label = { Text("Event Name") })

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.organizer,
                onValueChange = {
                    vm.organizer = it
                },
                placeholder = { Text("Organized by") },
                label = { Text("Event Organizer") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.description,
                onValueChange = {
                    vm.description = it
                },
                placeholder = { Text("About this event") },
                label = { Text("Event Description") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.contact,
                onValueChange = {
                    vm.contact = it
                },
                placeholder = { Text("Telephone/Fax/Email") },
                label = { Text("Contact Information") }
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = vm.location,
                onValueChange = {
                    vm.location = it
                },
                placeholder = { Text("Venue") },
                label = { Text("Location") }
            )

            val date = remember(vm.datePickerState.selectedDateMillis) {
                SimpleDateFormat("MM/dd/yyyy").format(
                    Date(
                        vm.datePickerState.selectedDateMillis ?: 0L
                    )
                )
            }
            FilledTonalButton(
                { showDatePicker = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Date: $date", style = MaterialTheme.typography.bodyLarge)
            }
            val time = remember(vm.timePickerState.hour, vm.timePickerState.minute) {
                val d = Date(
                    0,
                    0,
                    0,
                    vm.timePickerState.hour,
                    vm.timePickerState.minute
                )
                SimpleDateFormat("HH:mm aa").format(d)
            }
            FilledTonalButton(
                { showTimePicker = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Time: $time", style = MaterialTheme.typography.bodyLarge)
            }
            FilledTonalButton(
                onClick = { showCategory = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Category: ${stringResource(vm.selectedCategory.name)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            FilledTonalButton(
                onClick = { pickImage.launch("image/*") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Select Image",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            vm.selectedImageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Event Image",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(4 / 3f),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img)
                )
            }
        }
        if (showCategory) {
            CategorySelectDialog(
                onDismissRequest = { showCategory = false },
                selectedCategory = vm.selectedCategory,
                onSelectionChange = { vm.selectedCategory = it }
            )
        }
        if (showDatePicker) {
            DatePickerDialog(onDismissRequest = {
                showDatePicker = false
            }, confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            }) {
                DatePicker(state = vm.datePickerState)
            }
        }

        if (showTimePicker) {
            DatePickerDialog(onDismissRequest = {
                showTimePicker = false
            }, confirmButton = {
                TextButton(onClick = {
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            }) {
                TimePicker(state = vm.timePickerState, modifier = Modifier.fillMaxWidth())
            }
        }
    }
    with(vm) {
        if (addEventState != AddEventState.NoJob) {
            val notRunning = (addEventState != AddEventState.Uploading)
            AlertDialog(onDismissRequest = {
                if (notRunning) {
                    addEventState = AddEventState.NoJob
                }
            }, confirmButton = {
                Button(
                    onClick = { addEventState = AddEventState.NoJob },
                    enabled = (notRunning)
                ) {
                    Text(stringResource(id = R.string.okay))
                }
            }, title = {
                when (addEventState) {
                    AddEventState.Failed -> {
                        Text(stringResource(R.string.add_event_failed))
                    }

                    AddEventState.Uploading -> {
                        Text(stringResource(R.string.uploading_event))
                    }

                    AddEventState.Success -> {
                        Text(stringResource(R.string.add_event_successful))
                    }

                    else -> {}
                }
            }, text = {
                val image = AnimatedImageVector.animatedVectorResource(
                    id = R.drawable.calendar_anim
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .alpha(0.3f)
                ) {
                    Image(
                        modifier = Modifier.size(350.dp),
                        painter = painterResource(id = R.drawable.blob),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
                    )
                    Image(
                        modifier = Modifier.size(250.dp),
                        painter = rememberAnimatedVectorPainter(
                            animatedImageVector = image,
                            atEnd = notRunning
                        ),
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        contentDescription = null
                    )
                }
            })
        }
    }
}

@Composable
fun CategorySelectDialog(
    onDismissRequest: () -> Unit,
    selectedCategory: Category,
    onSelectionChange: (category: Category) -> Unit
) {
    AlertDialog(onDismissRequest = { onDismissRequest.invoke() }, confirmButton = {
        TextButton(onClick = {
            onDismissRequest.invoke()
        }) {
            Text("OK")
        }
    }, text = {
        LazyColumn(modifier = Modifier.height(500.dp)) {
            items(items = categories) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onSelectionChange(item)
                        })
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = item.icon, contentDescription = null)
                        Text(
                            text = stringResource(item.name)
                        )
                    }
                    RadioButton(
                        selected = (item == selectedCategory),
                        onClick = {
                            onSelectionChange(item)
                        }
                    )
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AddEventScreen()
}
