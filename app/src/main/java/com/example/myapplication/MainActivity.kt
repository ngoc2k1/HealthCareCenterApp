package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.patient.AccountFragment
import com.example.myapplication.patient.HomePatientFragment
import com.example.myapplication.patient.NotificationFragment
import com.example.myapplication.patient.ScheduleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this@MainActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this@MainActivity.resources.getColor(R.color.background_main)

        loadFragment(HomePatientFragment())

        binding.bottomMainNavigate.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.account ->
                    loadFragment(AccountFragment())

                R.id.home ->
                    loadFragment(HomePatientFragment())

                R.id.notification ->
                    loadFragment(NotificationFragment())

                R.id.schedule ->
                    loadFragment(ScheduleFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}