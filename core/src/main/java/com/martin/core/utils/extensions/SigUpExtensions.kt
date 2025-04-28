package com.martin.core.utils.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import com.martin.core.db.SignUpRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


fun String.toPart(): RequestBody =
    this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Uri.toMultipart(name: String, context: Context): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(this) ?: return null

    val mimeType = contentResolver.getType(this) ?: "image/*"
    val fileExt = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    val fileName = contentResolver.getFileName(this) ?: "$name-${System.currentTimeMillis()}.$fileExt"

    val requestBody = inputStream.readBytes().toRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, fileName, requestBody)
}


fun ContentResolver.getFileName(uri: Uri): String? {
    val cursor = this.query(uri, null, null, null, null) ?: return null
    return cursor.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameIndex)
    }
}

fun SignUpRequest.toMultipartParts(context: Context): Triple<Map<String, RequestBody>, MultipartBody.Part?, MultipartBody.Part?> {
    val data = mapOf(
        "fullName" to fullName.toPart(),
        "email" to email.toPart(),
        "password" to password.toPart(),
        "username" to username.toPart()
    )
    val avatarPart = avatarUri?.toMultipart("avatar", context)
    val coverPart = coverImageUri?.toMultipart("coverImage", context)

    return Triple(data, avatarPart, coverPart)
}





