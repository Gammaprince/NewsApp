package com.greenchilli.app.ui.viewmodel

import android.icu.text.LocaleDisplayNames.DialectHandling
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.data.FamousFoodRepository
import com.greenchilli.app.data.MenuBottomSheetRepository
import com.greenchilli.app.data.OffersRepository
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val famousFoodRepository: FamousFoodRepository,
    private val offerRepo: OffersRepository,
    private val addToCart: AddToCartRepository
) : ViewModel() {
    val famousFoodList: LiveData<Resource<List<FamousFoodResponse>>>
        get() = famousFoodRepository.famousFoodList
    val isAddedToCart: LiveData<Resource<String>>
        get() = addToCart.isAddedToCart

    val offerLIst: LiveData<List<String>>
        get() = offerRepo.offersList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            famousFoodRepository.getFamousFoodList()
        }
        viewModelScope.launch {
            offerRepo.getOffers()
        }
    }

    fun addToCart(image: String, foodName: String, price: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCart.addToCart(image, foodName, price)
        }
    }

    // saving java object to firebase
    fun setFamousFoodItem() {
        viewModelScope.launch(Dispatchers.IO) {
            famousFoodRepository.setFamousFoodItem()
        }
    }

    fun setOffers(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            offerRepo.setOffers(link)
        }

    }

}