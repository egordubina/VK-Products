package ru.egordubina.products.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.egordubina.products.repositories.ProductsRepository
import ru.egordubina.products.usecases.GetProductsUseCase
import ru.egordubina.products.usecases.GetProductsUseCaseImpl
import ru.egordubina.products.usecases.SearchProductsUseCase
import ru.egordubina.products.usecases.SearchProductsUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductsRepository): GetProductsUseCase =
        GetProductsUseCaseImpl(productsRepository = productsRepository)

    @Provides
    fun provideSearchProductsUseCase(productsRepository: ProductsRepository): SearchProductsUseCase =
        SearchProductsUseCaseImpl(productsRepository = productsRepository)
}