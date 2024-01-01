package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.SearchRepository
import com.greenchilli.app.ui.viewmodel.SearchViewModel

class SearchViewModelFactory(private val searchRepository : SearchRepository , private val addToCartRepository: AddToCartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(searchRepository , addToCartRepository) as T
    }
}