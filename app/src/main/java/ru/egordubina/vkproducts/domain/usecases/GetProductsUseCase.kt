package ru.egordubina.vkproducts.domain.usecases

import ru.egordubina.vkproducts.domain.Product
import ru.egordubina.vkproducts.domain.ProductsRepository

interface GetProductsUseCase {
    suspend fun getAllProducts(): List<Product>
}

class GetProductsUseCaseImpl(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override suspend fun getAllProducts(): List<Product> = productsRepository.getAllProducts()
}