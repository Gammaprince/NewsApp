package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.FamousFoodRepository
import com.greenchilli.app.data.OffersRepository
import com.greenchilli.app.ui.viewmodel.CartFragmentViewModel
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel

class CartFragmentViewModelFactory(private val repository: AddToCartRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartFragmentViewModel(repository) as T
    }
}