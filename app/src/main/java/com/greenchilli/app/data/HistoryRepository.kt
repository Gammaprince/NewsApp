package com.greenchilli.app.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse

class HistoryRepository {
    private val _listOfPurchasedItem = MutableLiveData<Resource<List<CartFragmentResponse>>>()
    val listOfPurchasedItem : LiveData<Resource<List<CartFragmentResponse>>>
    get() = _listOfPurchasedItem
    private val uid = FirebaseAuth.getInstance().uid.toString()

    suspend fun getItem(){
        _listOfPurchasedItem.postValue(Resource.Loading())
        FirebaseHelper.getFirebaseRef("User/${uid}/previous/").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CartFragmentResponse>()
                for(children in snapshot.children){
                    val data = children.getValue(CartFragmentResponse::class.java)!!
                    list.add(data)
                }
                _listOfPurchasedItem.postValue(Resource.Success(list))
            }
            override fun onCancelled(error: DatabaseError) {
                _listOfPurchasedItem.postValue(Resource.Error("something went wrong"))
            }
        })
    }
}