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
import ru.egordubina.vkproducts.ui.products.screens.all.ProductsScreen
import ru.egordubina.vkproducts.ui.products.screens.all.ProductsViewModel
import ru.egordubina.vkproducts.ui.products.screens.detail.ProductDetailScreen

@Composable
fun VkProductsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = VkProductsDestinations.PRODUCTS.name,
        modifier = modifier
    ) {
        composable(VkProductsDestinations.PRODUCTS.name) {
            val vm: ProductsViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            ProductsScreen(
                uiState = uiState,
                refreshAction = { vm.refresh() },
                loadData = { vm.loadNextPage(it) },
                navigateToCategories = { navController.navigate(VkProductsDestinations.CATEGORIES.name) }
            )
        }
        composable(
            "${VkProductsDestinations.DETAIL.name}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductDetailScreen()
        }
        composable(VkProductsDestinations.CATEGORIES.name) {
            CategoriesScreen()
        }
    }
}