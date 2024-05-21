package com.ardxclient.absenspn.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateTimeUtils {
    fun getCurrentDateFormatted(): String {
        val locale = Locale("id", "ID") // Locale untuk Indonesia
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        val currentDate = Calendar.getInstance().time
        return dateFormat.format(currentDate)
    }
}