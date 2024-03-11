package ru.egordubina.products.repositories

import ru.egordubina.products.models.Product
import ru.egordubina.products.models.asDomain
import ru.egordubina.products.network.ProductsApi
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
) : ProductsRepository {
    override suspend fun getAllProducts(page: Int, category: String): List<Product> =
        productsApi.loadProducts(page = page, category = category).products.map { it.asDomain() }
}