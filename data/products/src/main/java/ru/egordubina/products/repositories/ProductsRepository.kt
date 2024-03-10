package ru.egordubina.products.repositories

import ru.egordubina.products.models.Product
import ru.egordubina.products.models.asDomain
import ru.egordubina.products.network.ProductsApi

class ProductsRepositoryImpl(
    private val productsApi: ProductsApi,
) : ProductsRepository {
    override suspend fun getAllProducts(): List<Product> =
        productsApi.loadProducts(1).products.map { it.asDomain() }
}