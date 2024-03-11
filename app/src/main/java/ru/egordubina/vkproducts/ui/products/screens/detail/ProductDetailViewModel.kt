package ru.egordubina.vkproducts.ui.products.screens.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel() : ViewModel() {
    private val _uiState: MutableStateFlow<ProductDetailUiState> = MutableStateFlow(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {

    }
}