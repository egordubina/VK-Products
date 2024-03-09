package ru.egordubina.vkproducts.domain

interface ProductsRepository {
    suspend fun getAllProducts(): List<Product>
}