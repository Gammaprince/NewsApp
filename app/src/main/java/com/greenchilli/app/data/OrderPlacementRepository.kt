package com.greenchilli.app.data

import android.content.Context
import android.content.SharedPreferences
import com.greenchilli.app.GreenChilliApplication
import com.greenchilli.app.domain.FirebaseHelper

class OrderPlacementRepository(private val context : Context) {
    private val firebase : FirebaseHelper = FirebaseHelper
    private val sharedPreferences = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)
    private val uid = sharedPreferences.getString("UID","");
    private val ref = firebase.getFirebaseRef("User/${uid}")
    suspend fun getName(modePrivate: Int):String{
        return ref.child("name").get().toString()
    }
}