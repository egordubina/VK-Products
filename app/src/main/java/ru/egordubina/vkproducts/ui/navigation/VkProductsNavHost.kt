package ru.egordubina.vkproducts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.egordubina.vkproducts.ui.categories.CategoriesScreen
import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.screens.all.ProductsScreen
import ru.egordubina.vkproducts.ui.products.screens.all.ProductsViewModel
import ru.egordubina.vkproducts.ui.products.screens.detail.ProductDetailScreen
import ru.egordubina.vkproducts.ui.products.screens.detail.ProductDetailViewModel
import ru.egordubina.vkproducts.ui.search.SearchScreen
import ru.egordubina.vkproducts.ui.search.SearchViewModel

@Composable
internal fun VkProductsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = VkProductsDestinations.PRODUCTS.name,
        modifier = modifier
    ) {
        composable(
            "${VkProductsDestinations.PRODUCTS.name}?category={category}",
            arguments = listOf(navArgument("category") { defaultValue = "" })
        ) {
            val vm: ProductsViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            ProductsScreen(
                uiState = uiState,
                refreshAction = { vm.refresh() },
                loadData = { vm.loadNextPage(it, CategoryType.ALL.query) },
                onCategoryButtonClick = {
                    navController.navigate("${VkProductsDestinations.CATEGORIES.name}?category=${it.query}")
                },
                clearSelectedCategory = {
                    navController.navigate("${VkProductsDestinations.PRODUCTS.name}?category={category}") {
                        popUpTo("${VkProductsDestinations.PRODUCTS.name}?category={category}") {
                            inclusive = true
                        }
                    }
                },
                onItemClick = {
                    navController.navigate("${VkProductsDestinations.DETAIL.name}/$it")
                },
                onSearchButtonClick = {
                    navController.navigate(VkProductsDestinations.SEARCH.name)
                }
            )
        }
        composable(
            "${VkProductsDestinations.DETAIL.name}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            val vm: ProductDetailViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            ProductDetailScreen(
                uiState = uiState,
                onBackButtonClick = { navController.popBackStack() },
                refreshAction = { vm.refresh() }
            )
        }
        composable(
            "${VkProductsDestinations.CATEGORIES.name}?category={category}",
            arguments = listOf(navArgument("category") { defaultValue = "" })
        ) {
            CategoriesScreen(
                selectedCategory = CategoryType[it.arguments?.getString("category") ?: ""]
                    ?: CategoryType.ALL,
                onCategoryClick = {
                    navController.navigate("${VkProductsDestinations.PRODUCTS.name}?category=${it.query}") {
                        popUpTo("${VkProductsDestinations.PRODUCTS.name}?category={category}") {
                            inclusive = true
                        }
                    }
                },
                onBackButtonClick = { navController.popBackStack() }
            )
        }
        composable(VkProductsDestinations.SEARCH.name) {
            val vm: SearchViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            SearchScreen(
                uiState = uiState,
                onBackButtonAction = { navController.popBackStack() },
                onSearchButtonClick = { vm.searchProducts(query = it, page = 1) },
                loadData = { q, page -> vm.loadNextPage(query = q, page = page) },
                onSearchItemClick = {
                    navController.navigate("${VkProductsDestinations.DETAIL.name}/$it")
                }
            )
        }
    }
}