package app.suhasdissa.calendar.backend.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddEventApi {
    @Multipart
    @POST("api/v1/new")
    suspend fun addNewEvent(
        @Part("name") name: String,
        @Part image: MultipartBody.Part,
        @Part("time") time: String,
        @Part("description") description: String,
        @Part("category") category: String,
        @Part("organizer") organizer: String,
        @Part("location") location: String,
        @Part("contact") contact: String
    ): String
}
