package ru.egordubina.vkproducts.ui.products.screens.all

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.categories.CategoryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    uiState: ProductsUiState,
    refreshAction: () -> Unit,
    loadData: (Int) -> Unit,
    navigateToCategories: (CategoryType) -> Unit,
    clearSelectedCategory: () -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { navigateToCategories(if (uiState is ProductsUiState.Success) uiState.selectedCategory else CategoryType.ALL) }) {
                            Icon(imageVector = Icons.Rounded.Tune, contentDescription = null)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                    scrollBehavior = scrollAppBarBehavior,
                )
                if (uiState is ProductsUiState.Success)
                    LazyRow(
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                    ) {
                        item {
                            FilterChip(
                                selected = uiState.selectedCategory != CategoryType.ALL,
                                onClick = {
                                    if (uiState.selectedCategory == CategoryType.ALL)
                                        navigateToCategories(uiState.selectedCategory)
                                },
                                label = {
                                    Text(
                                        text = stringResource(
                                            id = CategoryType[uiState.selectedCategory.query.ifEmpty { "null" }]?.title
                                                ?: R.string.category_label__select
                                        )
                                    )
                                },
                                trailingIcon = {
                                    if (uiState.selectedCategory != CategoryType.ALL)
                                        Icon(
                                            imageVector = Icons.Rounded.Close,
                                            contentDescription = null,
                                            modifier = Modifier.clickable { clearSelectedCategory() }
                                        )
                                }
                            )
                        }
                    }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier
            .nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) { innerPadding ->
        when (uiState) {
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
                products = uiState.products,
                selectedCategory = uiState.selectedCategory,
                innerPadding = innerPadding,
                loadData = { page -> loadData(page) },
                onItemClick = { onItemClick(it) },
            )
        }
    }
}