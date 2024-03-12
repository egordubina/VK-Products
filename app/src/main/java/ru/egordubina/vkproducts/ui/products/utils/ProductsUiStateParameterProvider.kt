package ru.egordubina.vkproducts.ui.products.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.ProductUi
import ru.egordubina.vkproducts.ui.products.screens.all.ProductsUiState

internal class ProductsUiStateParameterProvider : PreviewParameterProvider<ProductsUiState> {
    override val values: Sequence<ProductsUiState>
        get() = sequenceOf(
            ProductsUiState(
                selectedCategory = CategoryType.ALL,
                products = listOf(
                    ProductUi(
                        id = 0,
                        title = "Google Pixel 10 Pro",
                        description = "A new phone from Google!!!",
                        priceWithDiscount = "$10,000",
                        price = "$15,000",
                        discountPercentage = 30,
                        rating = 5f,
                        stock = 1,
                        brand = "Google",
                        category = "Smartphones",
                        thumbnail = "",
                        images = emptyList()
                    ),
                    ProductUi(
                        id = 1,
                        title = "Google Pixel 10 Pro",
                        description = "A new phone from Google!!!",
                        priceWithDiscount = "$10,000",
                        price = "$15,000",
                        discountPercentage = 30,
                        rating = 5f,
                        stock = 1,
                        brand = "Google",
                        category = "Smartphones",
                        thumbnail = "",
                        images = emptyList()
                    ),
                )
            ),
            ProductsUiState(
                selectedCategory = CategoryType.SMARTPHONES,
                products = listOf(
                    ProductUi(
                        id = 0,
                        title = "Google Pixel 10 Pro",
                        description = "A new phone from Google!!!",
                        priceWithDiscount = "$10,000",
                        price = "$15,000",
                        discountPercentage = 30,
                        rating = 5f,
                        stock = 1,
                        brand = "Google",
                        category = "Smartphones",
                        thumbnail = "",
                        images = emptyList()
                    ),
                    ProductUi(
                        id = 1,
                        title = "Google Pixel 10 Pro",
                        description = "A new phone from Google!!!",
                        priceWithDiscount = "$10,000",
                        price = "$15,000",
                        discountPercentage = 30,
                        rating = 5f,
                        stock = 1,
                        brand = "Google",
                        category = "Smartphones",
                        thumbnail = "",
                        images = emptyList()
                    ),
                )
            ),
        )
}