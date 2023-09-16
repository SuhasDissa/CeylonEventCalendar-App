package app.suhasdissa.calendar

sealed class Destination(val route: String) {
    object Home : Destination("home")
    object Settings : Destination("settings")
    object About : Destination("about")
    object Search : Destination("search")
    object Category : Destination("category")

    object AddEvent : Destination("addnew")
}
