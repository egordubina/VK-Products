package ru.egordubina.products.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.egordubina.products.models.ResponseDTO
import javax.inject.Inject

interface ProductsApi {
    suspend fun loadProducts(page: Int): ResponseDTO // if page = 1 -> skip = (page - 1) * 20, limit = 20
}


class ProductsApiImpl @Inject constructor(
    private val baseUrl: String,
    private val client: HttpClient,
) : ProductsApi {
    override suspend fun loadProducts(page: Int): ResponseDTO {
        val skip = (page - 1) * 2
        val response = client.get("$baseUrl?skip=$skip&limit=20").bodyAsText()
        return Json.decodeFromString(response)
    }
}