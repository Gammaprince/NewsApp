package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.FamousFoodRepository
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.data.OffersRepository
import com.greenchilli.app.data.SearchRepository
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel
import com.greenchilli.app.ui.viewmodel.MenuBottomSheetViewModel

class MenutBottomSheetViewModelFactory (private val repository: MenuBottomSheetRepository , private val searchRepository: SearchRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MenuBottomSheetViewModel(repository , searchRepository) as T
    }
}