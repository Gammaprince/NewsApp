package com.greenchilli.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.data.HistoryRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.CartFragmentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragmentViewModel(private val repository: HistoryRepository) : ViewModel() {
    val getItem : LiveData<Resource<List<CartFragmentResponse>>>
    get() = repository.listOfPurchasedItem
    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getItem()
        }
    }
}