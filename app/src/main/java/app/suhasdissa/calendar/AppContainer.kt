package app.suhasdissa.calendar

import app.suhasdissa.calendar.backend.api.AddEventApi
import app.suhasdissa.calendar.backend.api.EventApi
import app.suhasdissa.calendar.backend.repositories.EventRepository
import app.suhasdissa.calendar.backend.repositories.EventRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val eventApi: EventApi
    val addEventApi: AddEventApi
    val eventRepository: EventRepository
}

const val BASE_URL = "https://ceylon-event-cal.onrender.com/"

class DefaultAppContainer : AppContainer {

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitPost = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    override val eventApi: EventApi by lazy {
        retrofit.create(EventApi::class.java)
    }

    override val addEventApi: AddEventApi by lazy {
        retrofitPost.create(AddEventApi::class.java)
    }

    override val eventRepository by lazy {
        EventRepositoryImpl(eventApi, addEventApi)
    }
}
