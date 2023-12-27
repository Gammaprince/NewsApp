package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.FamousFoodRepository
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.data.OffersRepository
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel

class HomeFragmentViewModelFactory(private val repository: FamousFoodRepository, private val offerRep : OffersRepository , private val addToCartRepository: AddToCartRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(repository , offerRep , addToCartRepository) as T
    }
}