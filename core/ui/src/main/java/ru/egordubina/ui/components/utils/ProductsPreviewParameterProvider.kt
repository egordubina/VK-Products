package ru.egordubina.ui.components.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ProductsPreviewParameterProvider : PreviewParameterProvider<ProductUi> {
    override val values: Sequence<ProductUi>
        get() = sequenceOf(
            ProductUi(id = 0,
                title = "Google Pixel 10 Pro",
                description = "A new phone from Google!!!",
                priceWithDiscount = "$10,000",
                price = "$15,000",
                discountPercentage = 30,
                rating = 5f,
                stock = 1,
                brand = "Google",
                category = "smartphones",
                thumbnail = "",
                images = emptyList()
            ),
        )
}

data class ProductUi(
    val id: Int,
    val title: String,
    val description: String,
    val priceWithDiscount: String,
    val price: String,
    val discountPercentage: Int,
    val rating: Float,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)