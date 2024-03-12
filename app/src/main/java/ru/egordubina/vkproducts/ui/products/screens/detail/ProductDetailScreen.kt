package ru.egordubina.vkproducts.ui.products.screens.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ru.egordubina.vkproducts.ui.products.utils.ProductsPreviewParameterProvider
import ru.egordubina.vkproducts.ui.theme.VkProductsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onBackButtonClick: () -> Unit,
    refreshAction: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackButtonClick() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { }, enabled = false) {
                            Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                        }
                        IconButton(onClick = {
                            scope.launch {
                                snackBarHostState.showSnackbar(message = "The product has been added to favorites")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }
                )
                if (uiState.isLoading)
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
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
        ProductDetailSuccess(
            product = uiState.product,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
internal fun ProductDetailSuccess(
    product: ProductUi,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        if (product.images.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { product.images.size })
            HorizontalPager(
                state = pagerState
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.images[it])
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(16f / 10f)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                repeat(product.images.size) {
                    val dotColor by animateColorAsState(
                        targetValue = if (it == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                        label = ""
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(dotColor)
                            .size(8.dp)
                    )
                }
            }
        }
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (product.rating > 0)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            if (product.discountPercentage > 0)
                Text(
                    text = "-${product.discountPercentage}%",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .alignByBaseline()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(4.dp)
                )
            if (product.brand.isNotEmpty())
                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .alignByBaseline()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(4.dp)
                )
            if (product.category.isNotEmpty())
                if (CategoryType[product.category]?.title != null) {
                    Text(
                        text = stringResource(id = CategoryType[product.category]!!.title),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .alignByBaseline()
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(4.dp)
                    )
                }
        }
        if (product.title.isNotEmpty())
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            if (product.priceWithDiscount.isNotEmpty())
                Text(
                    text = product.priceWithDiscount,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .alignByBaseline()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(8.dp)
                )
            if (product.price.isNotEmpty())
                Text(
                    text = product.price,
                    textDecoration = if (product.priceWithDiscount.isNotEmpty()) TextDecoration.LineThrough else null,
                    style = if (product.priceWithDiscount.isEmpty()) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.titleMedium,
                    fontWeight = if (product.priceWithDiscount.isEmpty()) FontWeight.Bold else null,
                    modifier = Modifier.alignByBaseline()
                )
            if (product.discountPercentage > 0)
                Text(
                    text = "-${product.discountPercentage}%",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.alignByBaseline()
                )
        }
        if (product.stock > 0)
            Text(
                text = stringResource(id = R.string.label__left_stock, product.stock),
                color = if (product.stock < 50) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (product.stock < 50) FontWeight.Bold else null,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        if (product.description.isNotEmpty()) {
            var isOpenDescription by rememberSaveable { mutableStateOf(false) }
            ElevatedCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .animateContentSize()
                    .clickable { isOpenDescription = !isOpenDescription }
                    .fillMaxWidth()
            ) {
                Text(
                    text = product.description,
                    maxLines = if (isOpenDescription) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductDetailPreview(
    @PreviewParameter(ProductsPreviewParameterProvider::class, limit = 1) product: ProductUi,
) {
    VkProductsTheme {
        ProductDetailScreen(
            uiState = ProductDetailUiState(product = product),
            {},
            {},
        )
    }
}