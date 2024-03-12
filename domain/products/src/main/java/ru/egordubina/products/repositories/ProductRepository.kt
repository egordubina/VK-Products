package ru.egordubina.products.repositories

import ru.egordubina.products.models.Product

interface ProductsRepository {
    suspend fun getAllProducts(page: Int, category: String): List<Product>
    suspend fun getProductById(id: Int): Product
    suspend fun searchProducts(query: String, page: Int): List<Product>
}