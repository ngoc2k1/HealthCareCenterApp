package com.example.myapplication.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*


object Constant {
    const val PICK_IMAGE_MULTIPLE = 1
    const val SHARED_PREFS = "SHARED_PREFS"
    const val USERNAME = "USERNAME"
    const val PASSWORD = "PASSWORD"
    const val SHARED_PREFS_DOCTOR = "SHARED_PREFS_DOCTOR"
    const val USERNAME_DOCTOR = "USERNAME_DOCTOR"
    const val PASSWORD_DOCTOR = "PASSWORD_DOCTOR"
    const val NAME_DOCTOR = "NAME_DOCTOR"
    const val AVT_DOCTOR = "AVT_DOCTOR"
    const val ID_BOOKSCHEDULE = "ID_BOOKSCHEDULE"
    const val ID_DOCTOR = "ID_DOCTOR"
    const val ID_PATIENT = "ID_PATIENT"
    const val IS_PATIENT = "IS_PATIENT"
    const val WEIGHT = "WEIGHT"
    const val HEIGHT = "HEIGHT"
    const val BLOOD = "BLOOD"
    const val AGE_PATIENT = "AGE_PATIENT"
    const val GENDER_PATIENT = "GENDER_PATIENT"
    const val ID_MEDICALHISTORY = "ID_MEDICALHISTORY"
    const val NAME_PATIENT = "NAME_PATIENT"
    const val CHAT = "CHAT"
    const val AVT_PATIENT = "AVT_PATIENT"
    const val AVT_FEMALE =
        "http://res.cloudinary.com/ngoc2012001/image/upload/v1701702806/healthcare/nqelyx7wuxqqiu6e6chw.png"
    const val AVT_FEMALE_DOCTOR =
        "http://res.cloudinary.com/ngoc2012001/image/upload/v1701700744/healthcare/iddoqbu7q7xk8afm0ixn.jpg"
    const val AVT_MALE_DOCTOR =
        "http://res.cloudinary.com/ngoc2012001/image/upload/v1701700640/healthcare/fjrhvnjw1a8lnqrsrayt.jpg"
    const val AVT_MALE =
        "http://res.cloudinary.com/ngoc2012001/image/upload/v1701703077/healthcare/folbtvcfv2xky5qyxrgc.png"
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) = requireActivity().toast(msg)

fun Activity.openCalendarDialog(textView: TextView) {
    val calendar = Calendar.getInstance()
    val mDatePicker = DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val formatter =
                SimpleDateFormat(resources.getString(R.string.str_ddmmyyyy))
            textView.text = formatter.format(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    mDatePicker.datePicker.maxDate = System.currentTimeMillis()
    mDatePicker.show()
}

fun getCurrentHour(): Int {
    val df = SimpleDateFormat("HH")
    return df.format(Calendar.getInstance().time).toInt()
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

fun convertGender(gender: String): String {
    return when (gender) {
        GENDER.FEMALE.toString() -> "nữ"
        GENDER.MALE.toString() -> "nam"
        else -> ""
    }
}

fun convertStatusBook(status: String, view: View): String {
    return when (status) {
        STATUS_BOOK.CHUA_KHAM.toString() -> view.context.getString(R.string.str_chuakham)
        STATUS_BOOK.DA_KHAM.toString() -> view.context.getString(R.string.str_dakham)
        else -> view.context.getString(R.string.str_dahuy)
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun String.generateQR(): Bitmap {

    val write = MultiFormatWriter()
    val hints = hashMapOf<EncodeHintType, Any>()
    hints[EncodeHintType.CHARACTER_SET] = Charset.forName("UTF-8")

    val matrix = write.encode(this, BarcodeFormat.QR_CODE, 400, 400, hints)
    val encoder = BarcodeEncoder()
    return encoder.createBitmap(matrix)
}