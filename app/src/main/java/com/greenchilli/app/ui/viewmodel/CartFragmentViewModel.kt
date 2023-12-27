package com.greenchilli.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartFragmentViewModel(private val repository: AddToCartRepository) : ViewModel() {
    val cartList : LiveData<Resource<List<CartFragmentResponse>>>
    get() = repository.cartList
    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getCart()
        }
    }
    fun deleteCart(foodName:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCart(foodName)
        }
    }
    fun addToCart(image:String,foodName: String,price:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.addToCart(image,foodName,price)
        }
    }
    fun decreaseCartCount(foodName: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.decreaseCartCount(foodName)
        }
    }
}