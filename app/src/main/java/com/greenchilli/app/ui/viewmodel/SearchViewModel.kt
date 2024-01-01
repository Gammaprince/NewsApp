package com.greenchilli.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.SearchRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository : SearchRepository , private val addToCartRepository: AddToCartRepository) : ViewModel() {
    val listOfItems : LiveData<Resource<List<FamousFoodResponse>>>
    get() = repository.listOfItems

    val isAddedToCart : LiveData<Resource<String>>
    get() = addToCartRepository.isAddedToCart
    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getItem()
        }
    }
    fun setItem(){
        viewModelScope.launch(Dispatchers.IO){
            repository.setItem()
        }
    }
    fun addToCart(image : String , foodName : String , price : String){
        viewModelScope.launch(Dispatchers.IO){
            addToCartRepository.addToCart(image,foodName,price)
        }
    }
}