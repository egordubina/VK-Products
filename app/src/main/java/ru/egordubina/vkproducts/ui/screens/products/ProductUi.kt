package ru.egordubina.vkproducts.ui.screens.products

import ru.egordubina.products.models.Product

data class ProductUi(
    val id: Int,
    val title: String,
    val description: String,
    val priceWithDiscount: Int,
    val price: Int,
    val discountPercentage: Int,
    val rating: Float,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
)

fun Product.asUi(): ProductUi = ProductUi(
    id = this.id,
    title = this.title,
    description = this.description,
    price = (this.price/(100 - this.discountPercentage) * 100).toInt(),
    priceWithDiscount = this.price,
    discountPercentage = this.discountPercentage.toInt(),
    rating = this.rating,
    stock = this.stock,
    brand = this.brand,
    category = this.category,
    thumbnail = this.thumbnail,
    images = this.images,
)