package com.greenchilli.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.MenuItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuBottomSheetViewModel(private val menuRepository : MenuBottomSheetRepository) : ViewModel() {
    val menuList : LiveData<Resource<List<MenuItemResponse>>>
        get() = menuRepository.menuList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.getMenu()
        }
    }
}