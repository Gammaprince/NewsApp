package com.greenchilli.app.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.greenchilli.app.domain.FirebaseHelper
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import com.greenchilli.app.model.FamousFoodResponse

class AddToCartRepository {
    // Getting Reference
    private val firebase : FirebaseHelper = FirebaseHelper
    // Getting Unique Id of current customer from firebase Auth
    private val uid : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

    // creating livedata for isAddedCart
    private val _isAddedToCart = MutableLiveData<Resource<String>>()
    val isAddedToCart : MutableLiveData<Resource<String>>
    get() = _isAddedToCart

    private val _cartList = MutableLiveData<Resource<List<CartFragmentResponse>>>()
    val cartList : LiveData<Resource<List<CartFragmentResponse>>>
    get() = _cartList




    // getting java object from firebase
    private val myRef = firebase.getFirebaseRef("User/${uid}/cart")

    suspend fun getCart(){
        _cartList.postValue(Resource.Loading())
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listCart = mutableListOf<CartFragmentResponse>()
                for(children in snapshot.children){
                    val model = children.getValue(CartFragmentResponse::class.java);
                    listCart.add(model!!)
                }
                _cartList.postValue(Resource.Success(listCart))
            }
            override fun onCancelled(error: DatabaseError){
                _cartList.postValue(Resource.Error(""))
            }
        })

    }
    suspend fun addToCart( image : String ,  foodName : String ,  price : String){
        _isAddedToCart.postValue(Resource.Loading())
        var model = CartFragmentResponse(image,foodName,price,1)
        val ref = firebase.getFirebaseRef("User/${uid}/cart/${foodName}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    model = snapshot.getValue(CartFragmentResponse::class.java)!!
                    model.count += 1
                    ref.setValue(model);
                }
                else ref.setValue(model)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    suspend fun decreaseCartCount(foodName: String){
        _isAddedToCart.postValue(Resource.Loading())
        val ref = firebase.getFirebaseRef("User/${uid}/cart/${foodName}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    val model = snapshot.getValue(CartFragmentResponse::class.java)!!
                    if(model.count > 1){
                        model.count -= 1
                        _isAddedToCart.postValue(Resource.Success(""))
                        ref.setValue(model);
                    }
                    else {
                        _isAddedToCart.postValue(Resource.Success(""))
                        ref.removeValue()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    suspend fun deleteCart(foodName:String){
        firebase.getFirebaseRef("User/${uid}/cart/${foodName}").removeValue()
    }
    suspend fun getName(): String {
        return FirebaseAuth.getInstance().currentUser.displayName.toString()
    }
}