package ru.egordubina.vkproducts.ui.products.screens.all

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.ProductUi
import ru.egordubina.vkproducts.ui.products.utils.CategoriesParameterProvider
import ru.egordubina.vkproducts.ui.products.utils.ProductsPreviewParameterProvider
import ru.egordubina.vkproducts.ui.products.utils.ProductsUiStateParameterProvider
import ru.egordubina.vkproducts.ui.products.utils.ProductsUiStateSuccessParameterProvider
import ru.egordubina.vkproducts.ui.theme.VkProductsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductsScreen(
    uiState: ProductsUiState,
    refreshAction: () -> Unit,
    loadData: (Int) -> Unit,
    onCategoryButtonClick: (CategoryType) -> Unit,
    clearSelectedCategory: () -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column(modifier = Modifier.animateContentSize()) {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { onCategoryButtonClick(uiState.selectedCategory) }) {
                            Icon(imageVector = Icons.Rounded.Tune, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.background),
                    scrollBehavior = scrollAppBarBehavior,
                )
                if (uiState.isLoading)
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                QuickActions(
                    selectedCategory = uiState.selectedCategory,
                    clearSelectedCategory = clearSelectedCategory,
                    onCategoryButtonClick = onCategoryButtonClick,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier.nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if (uiState.isError)
            LaunchedEffect(snackBarHostState) {
                scope.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = context.getString(R.string.label__error),
                        actionLabel = context.getString(R.string.label__retry)
                    )
                    if (result == SnackbarResult.ActionPerformed) refreshAction()
                }
            }
        BackHandler(uiState.selectedCategory != CategoryType.ALL) {
            clearSelectedCategory()
        }
        ProductsScreenContent(
            products = uiState.products,
            innerPadding = innerPadding,
            loadData = { page -> loadData(page) },
            onItemClick = { item -> onItemClick(item) }
        )
    }
}

@Composable
private fun ProductsScreenContent(
    products: List<ProductUi>,
    innerPadding: PaddingValues,
    loadData: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = innerPadding,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .consumeWindowInsets(innerPadding)
    ) {
        itemsIndexed(products, key = { _, item -> item.id }) { index, item ->
            ProductCard(productUi = item, onClick = { onItemClick(it) })
            // простенький "пагинатор", который неплохоо справляется со 100 элементами и в ОЗУ
            // занимает адеквантое место. При бОльшем кол-ве, конечноо же, нужно кэшировать и выгружать
            if (index == products.size - 10)
                LaunchedEffect(key1 = true) {
                    loadData(products.size / 20 + 1)
                }
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Composable
private fun QuickActions(
    selectedCategory: CategoryType,
    clearSelectedCategory: () -> Unit,
    onCategoryButtonClick: (CategoryType) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        item {
            CategoryChip(
                selectedCategory = selectedCategory,
                onChipClick = onCategoryButtonClick,
                clearSelectedCategory = clearSelectedCategory
            )
        }
    }
}

@Composable
private fun CategoryChip(
    selectedCategory: CategoryType,
    onChipClick: (CategoryType) -> Unit,
    clearSelectedCategory: () -> Unit,
) {
    FilterChip(
        selected = selectedCategory != CategoryType.ALL,
        onClick = {
            if (selectedCategory == CategoryType.ALL)
                onChipClick(selectedCategory)
        },
        label = {
            Text(
                text = stringResource(
                    id = CategoryType[selectedCategory.query.ifEmpty { "null" }]?.title
                        ?: R.string.category_label__select
                )
            )
        },
        trailingIcon = {
            if (selectedCategory != CategoryType.ALL)
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { clearSelectedCategory() }
                )
        }
    )
}

@Composable
private fun ProductCard(productUi: ProductUi, onClick: (Int) -> Unit) {
    Card(
        onClick = { onClick(productUi.id) },
    ) {
        ProductImage(thumbnail = productUi.thumbnail)
        ProductCardPrice(
            productUi.priceWithDiscount,
            productUi.price,
            productUi.discountPercentage
        )
        Text(
            text = productUi.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Text(
            text = productUi.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        ) {
            ProductCardRating(rating = productUi.rating.toString())
            Text(
                text = stringResource(id = R.string.label__left_stock_short, productUi.stock),
                color = if (productUi.stock < 50) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (productUi.stock < 50) FontWeight.Bold else null,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun ProductImage(thumbnail: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnail)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(bottom = 8.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun ProductCardRating(rating: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
        )
        Text(
            text = rating,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}

@Composable
private fun ProductCardPrice(
    priceWithDiscount: String,
    price: String,
    discountPercentage: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = priceWithDiscount,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = price,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = TextDecoration.LineThrough,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = "-${discountPercentage}%",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Preview
@Composable
private fun PreviewSuccessScreen(
    @PreviewParameter(ProductsUiStateSuccessParameterProvider::class) uiState: ProductsUiState,
) {
    VkProductsTheme {
        ProductsScreenContent(
            products = uiState.products,
            innerPadding = PaddingValues(0.dp),
            {},
            {}
        )
    }
}

@Preview
@Composable
private fun PreviewItemCard(
    @PreviewParameter(ProductsPreviewParameterProvider::class, limit = 1) product: ProductUi,
) {
    VkProductsTheme {
        ProductCard(product) {}
    }
}

@Preview
@Composable
private fun QuickActionsPreview(
    @PreviewParameter(CategoriesParameterProvider::class) category: CategoryType,
) {
    VkProductsTheme {
        QuickActions(
            selectedCategory = category,
            clearSelectedCategory = { }
        ) {}
    }
}

@Preview
@Composable
private fun CategoryChipPreview(
    @PreviewParameter(CategoriesParameterProvider::class) category: CategoryType,
) {
    VkProductsTheme {
        CategoryChip(
            selectedCategory = category,
            onChipClick = {},
            clearSelectedCategory = {}
        )
    }
}

@Preview
@Composable
private fun ProductsScreenSuccessPreview(
    @PreviewParameter(ProductsUiStateParameterProvider::class) uiState: ProductsUiState,
) {
    VkProductsTheme {
        ProductsScreen(
            uiState = uiState,
            refreshAction = {},
            loadData = {},
            onCategoryButtonClick = {},
            clearSelectedCategory = {},
            onItemClick = {},
        )
    }
}