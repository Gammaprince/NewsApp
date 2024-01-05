package com.greenchilli.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.model.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val repository: OrderPlacementRepository) : ViewModel() {
    val getInfo : LiveData<Resource<UserInfoResponse>>
    get() = repository.getInfo
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getInfo()
        }
    }
    fun setInfo(name : String , address : String , phone : String , email : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.setInfo(name,address,phone, email)
        }
    }
}