package com.greenchilli.app.data

import com.greenchilli.app.domain.FirebaseHelper

class OrderPlacementRepository {
    private val firebase : FirebaseHelper = FirebaseHelper

    private val ref = firebase.getFirebaseRef("User")
    suspend fun getName():String{
        return "ok"
    }
}