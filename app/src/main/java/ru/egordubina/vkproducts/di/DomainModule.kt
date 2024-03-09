package ru.egordubina.vkproducts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.egordubina.vkproducts.domain.ProductsRepository
import ru.egordubina.vkproducts.domain.usecases.GetProductsUseCase
import ru.egordubina.vkproducts.domain.usecases.GetProductsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase =
        GetProductsUseCaseImpl(productsRepository = productsRepository)
}