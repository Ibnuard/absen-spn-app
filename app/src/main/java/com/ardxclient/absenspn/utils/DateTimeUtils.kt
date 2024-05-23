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
    fun formatMonthYear(input: String): String? {
        return try {
            // Locale for Indonesia
            val localeID = Locale("in", "ID")

            // Input format
            val inputFormat = SimpleDateFormat("MM-yyyy", localeID)
            // Output format
            val outputFormat = SimpleDateFormat("MMMM yyyy", localeID)

            val date = inputFormat.parse(input)
            if (date != null) {
                outputFormat.format(date)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}