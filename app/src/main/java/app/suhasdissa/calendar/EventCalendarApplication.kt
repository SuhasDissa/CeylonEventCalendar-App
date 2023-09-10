package app.suhasdissa.calendar

import android.app.Application

class EventCalendarApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
