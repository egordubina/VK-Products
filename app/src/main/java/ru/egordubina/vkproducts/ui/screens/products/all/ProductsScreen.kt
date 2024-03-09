package ru.egordubina.vkproducts.ui.screens.products.all

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import ru.egordubina.vkproducts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    uiState: ProductsUiState,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    if (uiState == ProductsUiState.Error)
        LaunchedEffect(snackBarHostState) {
            scope.launch {
                snackBarHostState.showSnackbar(message = "Что-то пошло не так")
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Rounded.Tune, contentDescription = null)
                    }
                },
                scrollBehavior = scrollAppBarBehavior,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier
            .nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState,
            label = "",
        ) {
            when (it) {
                ProductsUiState.Error -> ProductScreenError(innerPadding = innerPadding)

                ProductsUiState.Loading -> ProductScreenLoading(innerPadding = innerPadding)

                ProductsUiState.Empty -> ProductsScreenEmpty()

                is ProductsUiState.Success -> ProductsScreenSuccess(
                    products = it.products,
                    innerPadding = innerPadding
                )
            }
        }
    }
}