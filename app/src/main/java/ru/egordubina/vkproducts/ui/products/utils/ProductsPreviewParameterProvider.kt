package ru.egordubina.vkproducts.ui.products.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.egordubina.vkproducts.ui.products.ProductUi

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
                category = "Smartphones",
                thumbnail = "",
                images = emptyList()
            ),
        )
}