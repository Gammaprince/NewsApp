package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.ui.viewmodel.OrderPlacementViewModel

class OrderPlacementViewModelFactory(private val orderPlacementRepository: OrderPlacementRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderPlacementViewModel(orderPlacementRepository) as T
    }
}