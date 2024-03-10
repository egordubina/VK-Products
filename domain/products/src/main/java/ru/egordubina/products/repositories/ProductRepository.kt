package ru.egordubina.products.repositories

import ru.egordubina.products.models.Product

interface ProductsRepository {
    suspend fun getAllProducts(): List<Product>
}