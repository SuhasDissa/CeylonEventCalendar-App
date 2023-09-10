package app.suhasdissa.calendar.backend.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

fun Uri.getExt(context: Context): String {
    val cR = context.contentResolver
    val mime = MimeTypeMap.getSingleton()
    return mime.getExtensionFromMimeType(cR.getType(this)) ?: "png"
}
