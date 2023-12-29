package com.greenchilli.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.FirebaseApp
import com.google.firebase.initialize
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class GreenChilliApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}