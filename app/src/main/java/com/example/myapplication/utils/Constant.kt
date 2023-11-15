package com.example.myapplication.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.*

object Constant {
    const val BASE_URL = "http://localhost:8080/api/v1/"
    const val USERNAME = "username"
    const val PASSWORD = "password"

    const val VALIDATE_TIME =
        "(?:(?:0[1-9]|1\\d|2[0-8])/(?:0[1-9]|1[0-2])|(?:29|30)/(?:0[13-9]|1[0-2])|31/(?:0[13578]|1[02]))/[1-9]\\d{3}|29/02(?:/[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00)"
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) = requireActivity().toast(msg)

fun Fragment.openCalendarDialog(editText: EditText) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        requireActivity(),
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val formatter = SimpleDateFormat(resources.getString(R.string.str_ddmmyyyy))
            editText.setText(formatter.format(calendar.timeInMillis))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Fragment.initDatePicker(editText: EditText, title: String) {
    val datePicker =
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
    datePicker.show(requireActivity().supportFragmentManager, "date picker")
    val formatter = SimpleDateFormat(resources.getString(R.string.str_ddmmyyyy))

    datePicker.addOnPositiveButtonClickListener {
        editText.setText(formatter.format(it))
    }
}

enum class BLOOD_GROUP {
    A,
    B,
    AB,
    O
}

enum class STATUS_BOOK {
    CHUA_KHAM,
    DA_KHAM,
    DA_HUY
}

enum class GENDER {
    MALE,
    FEMALE,
    OTHER
}

enum class ROLE {
    ADMIN,
    PATIENT,
    DOCTOR
}