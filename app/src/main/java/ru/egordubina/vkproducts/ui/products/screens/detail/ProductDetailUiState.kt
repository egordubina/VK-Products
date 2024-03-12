package ru.egordubina.vkproducts.ui.products.screens.detail

import ru.egordubina.vkproducts.ui.products.ProductUi

internal data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val product: ProductUi = ProductUi(
        id = 0,
        title = "",
        description = "",
        priceWithDiscount = "",
        price = "",
        discountPercentage = 0,
        brand = "",
        category = "",
        thumbnail = "",
        images = emptyList(),
        rating = 0f,
        stock = 0
    ),
)