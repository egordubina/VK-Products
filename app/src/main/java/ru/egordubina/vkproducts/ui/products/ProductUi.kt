package ru.egordubina.vkproducts.ui.products

import ru.egordubina.products.models.Product
import ru.egordubina.products.utils.toUsd

internal data class ProductUi(
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

internal fun Product.asUi(): ProductUi = ProductUi(
    id = this.id,
    title = this.title,
    description = this.description,
    price = (this.price/(100 - this.discountPercentage) * 100).toInt().toUsd(),
    priceWithDiscount = this.price.toUsd(),
    discountPercentage = this.discountPercentage.toInt(),
    rating = this.rating,
    stock = this.stock,
    brand = this.brand,
    category = this.category,
    thumbnail = this.thumbnail,
    images = this.images,
)