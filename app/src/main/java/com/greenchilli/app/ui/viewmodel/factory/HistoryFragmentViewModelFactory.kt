package com.greenchilli.app.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenchilli.app.data.HistoryRepository
import com.greenchilli.app.ui.viewmodel.HistoryFragmentViewModel

class HistoryFragmentViewModelFactory(private val repository: HistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryFragmentViewModel(repository) as T
    }
}