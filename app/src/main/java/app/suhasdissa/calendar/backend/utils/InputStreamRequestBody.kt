package app.suhasdissa.calendar.backend.utils

import android.content.ContentResolver
import android.net.Uri
import java.io.IOException
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

class InputStreamRequestBody(
    private val contentType: MediaType,
    private val contentResolver: ContentResolver,
    private val uri: Uri
) :
    RequestBody() {
    override fun contentType(): MediaType {
        return contentType
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        contentResolver.openInputStream(uri).use { stream ->
            sink.writeAll(stream?.source()!!)
        }
    }
}
