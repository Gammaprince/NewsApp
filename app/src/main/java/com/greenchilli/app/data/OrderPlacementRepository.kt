package com.greenchilli.app.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.auth.User
import com.greenchilli.app.GreenChilliApplication
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import com.greenchilli.app.model.UserInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderPlacementRepository(private val context: Context) {
    private val firebase: FirebaseHelper = FirebaseHelper
    private var totalPrice = 0
    private val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
//    private val uid = sharedPreferences.getString("UID", "")
    private val uid = FirebaseAuth.getInstance().uid.toString()
    private val ref = firebase.getFirebaseRef("UserInfo/${uid}/")
//    private val ref = firebase.getFirebaseRef("User/${uid}/cart")
//    private val _submitOrder = MutableStateFlow<Resource<String>>(0)
//    val submitOrder : StateFlow<Resource<String>>
//        get() = _submitOrder
    private val _getInfo = MutableLiveData<Resource<UserInfoResponse>>()
    val getInfo: LiveData<Resource<UserInfoResponse>>
        get() = _getInfo
    private val listOfCartFragmentResponse = mutableListOf<CartFragmentResponse>()
    private val _getTotalPrice = MutableLiveData<Resource<Int>>()
    val getTotalPrice: LiveData<Resource<Int>>
        get() = _getTotalPrice

    suspend fun getInfo() {
        _getInfo.postValue(Resource.Loading())
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(UserInfoResponse::class.java)!!
                _getInfo.postValue(Resource.Success(data))
            }

            override fun onCancelled(error: DatabaseError) {
                _getInfo.postValue(Resource.Error(message = "Error"))
            }
        })
    }

    suspend fun setInfo() {
        ref.setValue(UserInfoResponse("Prince", "Etawah", "917906060836", "PrinceLalu"));
    }

    suspend fun totalPrice() {
        _getTotalPrice.postValue(Resource.Loading())
        FirebaseDatabase.getInstance("https://anor-e2bd5-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User/${uid}/cart").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    totalPrice = 0
                    listOfCartFragmentResponse.clear()
                    for (ch in snapshot.children) {
                        val data = ch.getValue(CartFragmentResponse::class.java)!!
                        data.let { listOfCartFragmentResponse.add(it) }
                        totalPrice += (data.price.substring(1).toInt()*data.count)
                    }
                    Log.d("Paya"," cart has been changed "+listOfCartFragmentResponse)
                    _getTotalPrice.postValue(Resource.Success(totalPrice))
                }

                override fun onCancelled(error: DatabaseError) {
                    _getTotalPrice.postValue(Resource.Error(message = ""))
                }
            })
    }

    suspend fun submitOrder(): Resource<String> {
        // first -> deleting items from cart
        // second -> adding those item to pending folder
//        _submitOrder.emit(Resource.Loading())
        if(totalPrice > 0){
            for (cartFragmentResponse in listOfCartFragmentResponse) {
                firebase.getFirebaseRef("Pending/${uid}/").push().setValue(cartFragmentResponse)
                firebase.getFirebaseRef("User/${uid}/previous").push().setValue(cartFragmentResponse)
            }
            // adding uid to get all details of profile and even its pending items in admin app

            firebase.getFirebaseRef("PendingProfiles/${uid}/").setValue(uid)
            firebase.getFirebaseRef("User/${uid}/cart").removeValue()
            return Resource.Success("S")
        }
        else {
            return Resource.Error(message = "S")
        }

    }
}