package com.greenchilli.app.data

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide.init
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse

class FamousFoodRepository {
    private val firebase : FirebaseHelper = FirebaseHelper
    private val _famousFoodList = MutableLiveData<Resource<List<FamousFoodResponse>>>()
    private val list = mutableListOf<FamousFoodResponse>()
    val famousFoodList : LiveData<Resource<List<FamousFoodResponse>>>
    get() = _famousFoodList
    // getting java object from firebase
    val myRef = firebase.getFirebaseRef("FamousFoodItem/101")
    suspend fun getFamousFoodList(){

        _famousFoodList.postValue(Resource.Loading())
        val list = myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(childSnapshot in snapshot.children){
                    childSnapshot.getValue(FamousFoodResponse::class.java)?.let { list.add(it) }
                }
                _famousFoodList.postValue(Resource.Success(data = list))
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    // saving java object to firebase
    suspend fun setFamousFoodItem() {
        val image = "https://firebasestorage.googleapis.com/v0/b/anor-e2bd5.appspot.com/o/menu7.jpg?alt=media&token=9b996020-e0af-4a78-bd7e-92459daca77e"
        val model = FamousFoodResponse(image,"Mixed Fruits","₹5","This is very tasty , which fills with many rich Vitamins and minerals","Grapes,Banana,Apple")
        myRef.push().setValue(model)
    }
}