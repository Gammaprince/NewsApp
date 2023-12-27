package com.greenchilli.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.MenuItemResponse

class MenuBottomSheetRepository {
    private val firebase : FirebaseHelper = FirebaseHelper
    private val _menuList = MutableLiveData<Resource<List<MenuItemResponse>>>()
    private val list = mutableListOf<MenuItemResponse>()
    val menuList : LiveData<Resource<List<MenuItemResponse>>>
        get() = _menuList
    // getting java object from firebase
    val myRef = firebase.getFirebaseRef("menu")
    suspend fun getMenu(){
        _menuList.postValue(Resource.Loading())
        val list = myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(childSnapshot in snapshot.children){
                    childSnapshot.getValue(MenuItemResponse::class.java)?.let { list.add(it) }
                }
                _menuList.postValue(Resource.Success(data = list))
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    // saving java object to firebase
    suspend fun setFamousFoodItem() {
        val image = "https://firebasestorage.googleapis.com/v0/b/anor-e2bd5.appspot.com/o/menu2.jpg?alt=media&token=63dc58ff-0ae2-4885-990f-b86c75f6c43f"
        val model = MenuItemResponse(image,"Chat Masala","201 Rs")
        myRef.push().setValue(model)
    }
}