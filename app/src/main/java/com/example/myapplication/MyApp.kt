package com.example.myapplication

import android.app.Application
import com.orhanobut.hawk.Hawk

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build();
    }
}