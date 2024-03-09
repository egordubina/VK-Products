package ru.egordubina.vkproducts.data.repository

import ru.egordubina.vkproducts.data.network.ProductsApi
import ru.egordubina.vkproducts.domain.Product
import ru.egordubina.vkproducts.domain.ProductsRepository
import ru.egordubina.vkproducts.data.models.asDomain

class ProductsRepositoryImpl(
    private val productsApi: ProductsApi
) : ProductsRepository {
    override suspend fun getAllProducts(): List<Product> =
        productsApi.loadProducts(1).products.map { it.asDomain() }
}