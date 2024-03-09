package ru.egordubina.vkproducts.ui.screens.products.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.vkproducts.ui.screens.products.ProductUi

@Composable
internal fun ProductsScreenSuccess(
    products: List<ProductUi>,
    innerPadding: PaddingValues,
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
        items(products, key = { item -> item.id }) { item ->
            ProductCard(productUi = item, onClick = {})
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Composable
fun ProductCard(productUi: ProductUi, onClick: (ProductUi) -> Unit) {
    OutlinedCard(
        onClick = { onClick(productUi) },
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
                .fillMaxWidth()
        )
        Text(
            text = productUi.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Text(
            text = productUi.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Text(
            text = productUi.price.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}