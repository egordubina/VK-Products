//package ru.egordubina.products
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import ru.egordubina.products.models.Product
//import ru.egordubina.products.repositories.ProductsRepository
//import javax.inject.Inject
//
//class ProductsPagingDataSource @Inject constructor(
//    private val productsRepository: ProductsRepository,
//) : PagingSource<Int, Product>() {
//    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
//        val anchorPosition = state.anchorPosition ?: return null
//        val page = state.closestPageToPosition(anchorPosition) ?: return null
//        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
//        val page = params.key ?: 1
//        val pageSize = 20
//        return try {
//            val response = productsRepository.getAllProducts(page)
//            val nextKey = if (response.size < pageSize) null else page + 1
//            val prevKey = if (page == 1) null else page - 1
//            LoadResult.Page(
//                data = response,
//                nextKey = nextKey,
//                prevKey = prevKey
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}