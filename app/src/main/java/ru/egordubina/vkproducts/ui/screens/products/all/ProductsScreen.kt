package ru.egordubina.vkproducts.ui.screens.products.all

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.screens.products.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    vm: ProductsViewModel = hiltViewModel(),
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {},
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val uiState by vm.uiState.collectAsState()
        AnimatedContent(
            targetState = uiState,
            label = "",
        ) {
            when (it) {
                ProductsUiState.Error -> ProductScreenError()
                ProductsUiState.Loading -> ProductScreenLoading(
                    innerPadding = innerPadding
                )
                ProductsUiState.Empty -> ProductsScreenEmpty()
                is ProductsUiState.Success -> ProductsScreenSuccess(
                    products = it.products,
                    innerPadding = innerPadding
                )
            }
        }
    }
}