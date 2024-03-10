package ru.egordubina.vkproducts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import ru.egordubina.products.repositories.ProductsRepository
import ru.egordubina.vkproducts.BuildConfig
import ru.egordubina.vkproducts.data.network.ProductsApi
import ru.egordubina.vkproducts.data.network.ProductsApiImpl
import ru.egordubina.vkproducts.data.repository.ProductsRepositoryImpl

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