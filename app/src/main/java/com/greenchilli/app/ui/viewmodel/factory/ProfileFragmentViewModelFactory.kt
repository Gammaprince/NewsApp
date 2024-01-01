package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.OrderPlacementRepository
import com.greenchilli.app.ui.viewmodel.ProfileFragmentViewModel

class ProfileFragmentViewModelFactory(private val repository: OrderPlacementRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileFragmentViewModel(repository) as T
    }
}