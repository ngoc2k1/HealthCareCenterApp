package com.example.myapplication.doctor

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDoctorNotificationBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class NotificationDoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorNotificationBinding
    private lateinit var notificationItemAdapter: NotificationItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this@NotificationDoctorActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            statusBarColor = Color.TRANSPARENT
        }
        notificationItemAdapter = NotificationItemAdapter(this)

        binding.tvNoneNotification.setOnClickListener {
            generateQR()
        }
    }

    private fun generateQR() {
        val text = "NGUYEN NGOC UYEN"
        val write = MultiFormatWriter()
        try {
            val matrix = write.encode(text, BarcodeFormat.QR_CODE, 400, 400)
            val encoder = BarcodeEncoder()
            val bitmap = encoder.createBitmap(matrix)
//            binding.qr.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }


}