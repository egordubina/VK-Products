package ru.egordubina.vkproducts.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.vkproducts.data.models.ProductDTO

@Serializable
data class ResponseDTO(
    @SerialName("products") val products: List<ProductDTO>,
    @SerialName("total") val total: Int,
    @SerialName("skip") val skip: Int,
    @SerialName("limit") val limit: Int,
)
