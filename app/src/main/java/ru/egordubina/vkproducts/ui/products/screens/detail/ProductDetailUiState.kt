package ru.egordubina.vkproducts.ui.products.screens.detail

import ru.egordubina.products.models.Product
import ru.egordubina.vkproducts.ui.products.ProductUi

sealed class ProductDetailUiState {
    data object Loading : ProductDetailUiState()
    data object Error : ProductDetailUiState()
    data class Success(
        val product: ProductUi
    ): ProductDetailUiState()
}