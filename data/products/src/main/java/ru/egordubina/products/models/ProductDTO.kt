package ru.egordubina.products.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.products.models.Product

@Serializable
data class ProductDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Int,
    @SerialName("discountPercentage")
    val discountPercentage: Float,
    @SerialName("rating")
    val rating: Float,
    @SerialName("stock")
    val stock: Int,
    @SerialName("brand")
    val brand: String,
    @SerialName("category")
    val category: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("images")
    val images: List<String>,
)

fun ProductDTO.asDomain(): Product = Product(
    id = this.id,
    title = this.title,
    description = this.description,
    price = this.price,
    discountPercentage = this.discountPercentage,
    rating = this.rating,
    stock = this.stock,
    brand = this.brand,
    category = this.category,
    thumbnail = this.thumbnail,
    images = this.images,
)