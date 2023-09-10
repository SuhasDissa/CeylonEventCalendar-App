package app.suhasdissa.calendar.ui.screens.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.suhasdissa.calendar.Destination
import app.suhasdissa.calendar.backend.models.categories
import app.suhasdissa.calendar.backend.viewmodels.CategoryViewModel
import app.suhasdissa.calendar.ui.components.CategoryCard

@Composable
fun ExplorePage(
    onNavigate: (Destination) -> Unit,
    categoryViewModel: CategoryViewModel = viewModel(
        LocalContext.current as ComponentActivity,
        factory = CategoryViewModel.Factory
    )
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        Text(text = "Categories", style = MaterialTheme.typography.headlineMedium)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth().height(320.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) {
                CategoryCard(text = stringResource(id = it.name), icon = it.icon) {
                    categoryViewModel.searchByCategory(it)
                    onNavigate(Destination.Category)
                }
            }
        }
    }
}
