package ru.egordubina.vkproducts.ui.products.screens.all

import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.ProductUi

internal data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val selectedCategory: CategoryType = CategoryType.ALL,
    val products: List<ProductUi> = emptyList(),
)