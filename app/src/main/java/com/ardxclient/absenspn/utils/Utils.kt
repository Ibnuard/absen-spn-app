package com.ardxclient.absenspn.utils

import android.content.Context
import android.widget.Toast

object Utils {
    fun showToast(context: Context, message: String){
        return Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}