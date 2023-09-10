package app.suhasdissa.calendar.backend.api

import app.suhasdissa.calendar.backend.models.Event
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {
    @GET("api/v1/events")
    suspend fun getEvents(
        @Query("q") query: String? = null,
        @Query("category") category: String? = null
    ): List<Event>

    @GET("api/v1/event")
    suspend fun getEvent(
        @Query("id") id: String
    ): Event

    @GET("api/v1/suggestions")
    suspend fun getSearchSuggestions(
        @Query("q") query: String
    ): List<String>
}
