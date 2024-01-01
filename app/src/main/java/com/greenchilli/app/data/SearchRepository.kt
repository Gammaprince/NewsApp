package com.greenchilli.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.SearchViewResponse
import kotlinx.coroutines.delay

class SearchRepository {
    private val _lisfOfItems = MutableLiveData<Resource<List<FamousFoodResponse>>>()
    val listOfItems : LiveData<Resource<List<FamousFoodResponse>>>
    get() = _lisfOfItems
    private val uid = FirebaseAuth.getInstance().uid.toString()

    suspend fun setItem(){
        val data = FamousFoodResponse("https://firebasestorage.googleapis.com/v0/b/anor-e2bd5.appspot.com/o/menu6.jpg?alt=media&token=cb6424f4-c094-4db1-826f-47e4116edf1b","Roll","$4" , "Very Tasty Rich in Vitamins","Sugar,Salt")

        FirebaseHelper.getFirebaseRef("Items/").push().setValue(data)
    }

    suspend fun getItem(){
        _lisfOfItems.postValue(Resource.Loading())
        FirebaseHelper.getFirebaseRef("Items/").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<FamousFoodResponse>()
               for(children in snapshot.children){
                   val data = children.getValue(FamousFoodResponse::class.java)!!
                   list.add(data)
               }
                _lisfOfItems.postValue(Resource.Success(list))
            }

            override fun onCancelled(error: DatabaseError) {
                _lisfOfItems.postValue(Resource.Error(message = "Something went wrong"))
            }
        })
    }
}