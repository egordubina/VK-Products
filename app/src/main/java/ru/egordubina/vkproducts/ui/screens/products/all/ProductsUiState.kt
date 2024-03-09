package ru.egordubina.vkproducts.ui.screens.products.all

import ru.egordubina.vkproducts.ui.screens.products.ProductUi

sealed class ProductsUiState {
    data object Loading : ProductsUiState()
    data object Error : ProductsUiState()
    data object Empty : ProductsUiState()
    data class Success(val products: List<ProductUi>) : ProductsUiState()
}