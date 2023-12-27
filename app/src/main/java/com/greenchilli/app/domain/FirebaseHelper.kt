package com.greenchilli.app.domain

import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

object FirebaseHelper {
    private var BASE_URL = "https://anor-e2bd5-default-rtdb.asia-southeast1.firebasedatabase.app"
    fun getFirebaseRef(path : String) : DatabaseReference {
        return FirebaseDatabase.getInstance(BASE_URL).getReference("/"+path)
    }
}