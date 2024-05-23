package com.ardxclient.absenspn.utils

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import java.io.InputStream

object Utils {
    fun showToast(context: Context, message: String){
        return Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun uriToBase64(context: Context, fileUri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(fileUri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}