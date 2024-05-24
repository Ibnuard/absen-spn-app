package com.ardxclient.absenspn.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
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

    fun formatFullDate(input: String): String? {
        return try {
            // Locale for Indonesia
            val localeID = Locale("in", "ID")

            // Input format
            val inputFormat = SimpleDateFormat("dd-MM-yyyy", localeID)
            // Output format
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", localeID)

            // Parse the input date
            val date = inputFormat.parse(input)
            if (date != null) {
                // Format the date to the desired output
                outputFormat.format(date)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun formatDate(date: Long, pattern: String = "dd-MM-yyyy"): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())

        return dateFormat.format(calendar.time)
    }

    fun formatTime(h: Int, m: Int): String {
        var fHour = if (h<10) "0${h}" else h
        var fMinute = if (m<10) "0${m}" else m

        return "$fHour.$fMinute"
    }

    fun showDatePicker(activity: FragmentActivity, input: EditText){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih tanggal")
                .build()

        input.setOnClickListener {
            datePicker.show(activity.supportFragmentManager, "DATEPICKER")
        }

        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = formatDate(it)
            input.setText(selectedDate)
        }
    }
}