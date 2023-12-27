package com.greenchilli.app.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.model.FamousFoodResponse

class OffersRepository {
    private val firebase : FirebaseHelper = FirebaseHelper
    private val _offersList = MutableLiveData<List<String>>()
    val offersList : LiveData<List<String>>
    get() = _offersList
    val myRef = firebase.getFirebaseRef("offers")
    suspend fun getOffers(){
        val list = myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val offersImageList = ArrayList<String>()
                for(childSnapShot in snapshot.children){
                    val imageLink = childSnapShot.getValue() as String
                    offersImageList.add(imageLink);
                }
                _offersList.postValue(offersImageList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    suspend fun setOffers(link : String){
        val ImageLink = link
        myRef.push().setValue(link)
        myRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<String>()
                for (cc in snapshot.children){
                    list.add(cc.getValue() as String)
                }
                Log.d("SYSTEM HAIN"," --> ${list}")
                _offersList.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}