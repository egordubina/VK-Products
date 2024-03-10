package ru.egordubina.products.usecases

import ru.egordubina.products.models.Product
import ru.egordubina.products.repositories.ProductsRepository
import javax.inject.Inject

interface GetProductsUseCase {
    suspend fun getAllProducts(): List<Product>
}

class GetProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override suspend fun getAllProducts(): List<Product> = productsRepository.getAllProducts()
}