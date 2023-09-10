package app.suhasdissa.calendar.backend.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Festival
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Handyman
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.More
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.SportsSoccer
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.ui.graphics.vector.ImageVector
import app.suhasdissa.calendar.R

data class Category(
    val id: String,
    @StringRes val name: Int,
    val icon: ImageVector
)

val categories = listOf(
    Category("sports", R.string.sports, Icons.Rounded.SportsSoccer),
    Category("visual", R.string.visual_arts, Icons.Rounded.Palette),
    Category("performing", R.string.performing_arts, Icons.Rounded.TheaterComedy),
    Category("workshop", R.string.workshop, Icons.Rounded.Handyman),
    Category("books", R.string.lectures_and_books, Icons.Rounded.MenuBook),
    Category("competition", R.string.competition, Icons.Rounded.EmojiEvents),
    Category("community", R.string.community, Icons.Rounded.Groups),
    Category("educational", R.string.educational, Icons.Rounded.School),
    Category("festivals", R.string.festivals_and_fairs, Icons.Rounded.Festival),
    Category("other", R.string.other, Icons.Rounded.More)
)
