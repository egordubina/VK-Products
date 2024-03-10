package ru.egordubina.vkproducts.data.repository

import ru.egordubina.products.models.Product
import ru.egordubina.products.repositories.ProductsRepository
import ru.egordubina.vkproducts.data.models.asDomain
import ru.egordubina.vkproducts.data.network.ProductsApi

class ProductsRepositoryImpl(
    private val productsApi: ProductsApi
) : ProductsRepository {
    override suspend fun getAllProducts(): List<Product> =
        productsApi.loadProducts(1).products.map { it.asDomain() }
}