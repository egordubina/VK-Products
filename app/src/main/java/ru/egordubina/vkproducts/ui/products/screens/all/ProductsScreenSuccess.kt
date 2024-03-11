package ru.egordubina.vkproducts.ui.products.screens.all

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.ProductUi

@Composable
internal fun ProductsScreenSuccess(
    products: List<ProductUi>,
    selectedCategory: CategoryType,
    innerPadding: PaddingValues,
    loadData: (Int) -> Unit,
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
        itemsIndexed(products, key = { index, item -> item.id }) { index, item ->
            ProductCard(productUi = item, onClick = {})
            // простенький "пагинатор", который неплохоо справляется со 100 элементами и в ОЗУ
            // занимает адеквантое место. При бОльшем кол-ве, конечноо же нужно кэшировать и выгружать
            if (index == products.size - 10)
                LaunchedEffect(key1 = true) {
                    loadData(products.size / 20 + 1)
                }
        }
//        item {
//            Text(
//                text = "С ❤️ от VK",
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Composable
fun ProductCard(productUi: ProductUi, onClick: (Int) -> Unit) {
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = productUi.priceWithDiscount.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = productUi.price.toString(),
                textDecoration = TextDecoration.LineThrough,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "-${productUi.discountPercentage}%",
                style = MaterialTheme.typography.titleSmall
            )
        }
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
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                text = productUi.rating.toString(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}