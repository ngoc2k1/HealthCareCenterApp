package com.example.myapplication.doctor

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.background_main)

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
            binding.qr.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }


}