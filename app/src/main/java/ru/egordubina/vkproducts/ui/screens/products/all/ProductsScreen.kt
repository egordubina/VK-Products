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
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import ru.egordubina.vkproducts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    uiState: ProductsUiState,
    refreshAction: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

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
                ProductsUiState.Error -> {
                    LaunchedEffect(snackBarHostState) {
                        scope.launch {
                            val result = snackBarHostState.showSnackbar(
                                message = context.getString(R.string.label__error),
                                actionLabel = context.getString(R.string.label__retry)
                            )
                            if (result == SnackbarResult.ActionPerformed) refreshAction()
                        }
                    }
                    ProductScreenError(innerPadding = innerPadding)
                }

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