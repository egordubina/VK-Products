package ru.egordubina.products.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.egordubina.products.models.ProductDTO
import ru.egordubina.products.models.ResponseDTO
import javax.inject.Inject

interface ProductsApi {
    suspend fun loadProducts(page: Int, category: String): ResponseDTO // if page = 1 -> skip = (page - 1) * 20, limit = 20
    suspend fun loadProductById(id: Int): ProductDTO
}


class ProductsApiImpl @Inject constructor(
    private val baseUrl: String,
    private val client: HttpClient,
) : ProductsApi {
    override suspend fun loadProducts(page: Int, category: String): ResponseDTO {
        val skip = (page - 1) * 20
        val response = if (category.isEmpty())
            client.get("$baseUrl?skip=$skip&limit=20").bodyAsText()
        else
            client.get("$baseUrl/category/$category?skip=$skip&limit=20").bodyAsText()
        return Json.decodeFromString(response)
    }

    override suspend fun loadProductById(id: Int): ProductDTO {
        val response = client.get("$baseUrl/$id").bodyAsText()
        return Json.decodeFromString(response)
    }
}