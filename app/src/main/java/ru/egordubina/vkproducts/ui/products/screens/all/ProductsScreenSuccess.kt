package ru.egordubina.vkproducts.ui.products.screens.all

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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.vkproducts.ui.products.ProductUi
import ru.egordubina.vkproducts.ui.products.utils.ProductsPreviewParameterProvider
import ru.egordubina.vkproducts.ui.products.utils.ProductsUiStateSuccessParameterProvider
import ru.egordubina.vkproducts.ui.theme.VkProductsTheme

@Composable
internal fun ProductsScreenSuccess(
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
private fun ProductCard(productUi: ProductUi, onClick: (Int) -> Unit) {
    Card(
        onClick = { onClick(productUi.id) },
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(productUi.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = productUi.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )
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
                text = "Left: ${productUi.stock}",
                color = if (productUi.stock < 50) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (productUi.stock < 50) FontWeight.Bold else null,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
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
    @PreviewParameter(ProductsUiStateSuccessParameterProvider::class) uiState: ProductsUiState.Success,
) {
    VkProductsTheme {
        ProductsScreenSuccess(
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