package com.greenchilli.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import com.greenchilli.app.model.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderPlacementViewModel(private val repository : OrderPlacementRepository) : ViewModel(){
    val getInfo : LiveData<Resource<UserInfoResponse>>
        get() = repository.getInfo
    val getTotalPrice : LiveData<Resource<Int>>
        get() = repository.getTotalPrice
//    val submitOrder : StateFlow<Resource<String>>
//     get() = repository.submitOrder
    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getInfo()
            repository.totalPrice()
        }
    }
    suspend fun submitOrder(): Resource<String> {
        val job = viewModelScope.async(Dispatchers.IO){
            repository.submitOrder()
        }
        return job.await()
    }
    fun setInfo(){
        viewModelScope.launch(Dispatchers.IO){
            repository.setInfo()
        }
    }
}