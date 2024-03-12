package ru.egordubina.vkproducts.ui.products.screens.detail

import ru.egordubina.vkproducts.ui.products.ProductUi

internal sealed class ProductDetailUiState {
    data object Loading : ProductDetailUiState()
    data object Error : ProductDetailUiState()
    data class Success(
        val product: ProductUi
    ): ProductDetailUiState()
}