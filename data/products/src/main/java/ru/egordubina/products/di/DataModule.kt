package ru.egordubina.products.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import ru.egordubina.products.BuildConfig
import ru.egordubina.products.network.ProductsApi
import ru.egordubina.products.network.ProductsApiImpl
import ru.egordubina.products.repositories.ProductsRepository
import ru.egordubina.products.repositories.ProductsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun providesHttpClient(): HttpClient = HttpClient(CIO)

    @Provides
    fun providesProductsApi(client: HttpClient): ProductsApi =
        ProductsApiImpl(BuildConfig.API_URL, client)

    @Provides
    fun provideProductsRepository(productsApi: ProductsApi): ProductsRepository =
        ProductsRepositoryImpl(productsApi)
}