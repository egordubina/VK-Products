package ru.egordubina.vkproducts.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.products.ProductUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    uiState: SearchUiState,
    onBackButtonAction: () -> Unit,
    onSearchButtonClick: (String) -> Unit,
    onSearchItemClick: (Int) -> Unit,
    loadData: (String, Int) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    SearchBar(
        tonalElevation = 0.dp,
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = {
            onSearchButtonClick(it)
            keyboardController?.hide()
        },
        active = true,
        onActiveChange = { },
        leadingIcon = {
            IconButton(onClick = { onBackButtonAction() }) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }
        },
        trailingIcon = {
            AnimatedVisibility(searchQuery.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                }
            }
        },
        modifier = Modifier.focusRequester(focusRequester)
    ) {
        if (uiState.products.isNotEmpty())
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                itemsIndexed(uiState.products, key = { index, _ -> index }) { index, item ->
                    ProductCard(
                        productUi = item,
                        onClick = { onSearchItemClick(it) })
                    if (index == uiState.products.size - 10)
                        LaunchedEffect(key1 = true) {
                            loadData(searchQuery, uiState.products.size / 20 + 1)
                        }
                }
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                }
            }
        if (uiState.isError)
            Text(
                text = stringResource(id = R.string.label__error),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
    }
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    BackHandler { onBackButtonAction() }
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