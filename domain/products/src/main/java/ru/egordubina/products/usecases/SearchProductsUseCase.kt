package ru.egordubina.products.usecases

import ru.egordubina.products.models.Product
import ru.egordubina.products.repositories.ProductsRepository
import javax.inject.Inject

interface SearchProductsUseCase {
    suspend fun searchProducts(query: String, page: Int): List<Product>
}

class SearchProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
) : SearchProductsUseCase {
    override suspend fun searchProducts(query: String, page: Int): List<Product> =
        productsRepository.searchProducts(query, page)
}