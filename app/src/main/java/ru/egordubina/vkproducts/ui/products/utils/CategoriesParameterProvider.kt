package ru.egordubina.vkproducts.ui.products.utils

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.egordubina.vkproducts.ui.categories.CategoryType

internal class CategoriesParameterProvider : PreviewParameterProvider<CategoryType> {
    override val values: Sequence<CategoryType>
        get() = sequenceOf(
            CategoryType.ALL,
            CategoryType.SMARTPHONES,
        )
}