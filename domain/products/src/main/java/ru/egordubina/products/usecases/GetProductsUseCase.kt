package ru.egordubina.products.usecases

import ru.egordubina.products.models.Product
import ru.egordubina.products.repositories.ProductsRepository
import javax.inject.Inject

interface GetProductsUseCase {
    suspend fun getAllProducts(page: Int, category: String): List<Product>
}

class GetProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override suspend fun getAllProducts(page: Int, category: String): List<Product> =
        productsRepository.getAllProducts(page = page, category = category)
}