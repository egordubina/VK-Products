package ru.egordubina.vkproducts.ui.search

import ru.egordubina.vkproducts.ui.products.ProductUi

internal data class SearchUiState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val products: List<ProductUi> = emptyList()
)