package ru.egordubina.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.ui.R
import ru.egordubina.ui.components.utils.ProductUi
import ru.egordubina.ui.components.utils.ProductsPreviewParameterProvider
import ru.egordubina.ui.theme.VkProductsTheme

@Composable
fun ProductCard(
    id: Int,
    thumbnail: String,
    priceWithDiscount: String,
    price: String,
    discountPercentage: Int,
    title: String,
    description: String,
    rating: Float,
    stock: Int,
    onClick: (Int) -> Unit,
) {
    Card(
        onClick = { onClick(id) },
    ) {
        ProductImage(thumbnail = thumbnail)
        ProductCardPrice(
            priceWithDiscount,
            price,
            discountPercentage
        )
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
        )
        Text(
            text = description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
                .padding(bottom = dimensionResource(id = R.dimen.small_padding))
        ) {
            ProductCardRating(rating = rating.toString())
            Text(
                text = stringResource(id = R.string.label__left_stock_short, stock),
                color = if (stock < 50) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (stock < 50) FontWeight.Bold else null,
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
            .padding(bottom = dimensionResource(id = R.dimen.small_padding))
            .fillMaxWidth()
    )
}

@Composable
private fun ProductCardRating(rating: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.very_small_padding)),
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
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.very_small_padding)),
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.small_padding))
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
private fun PreviewItemCard(
    @PreviewParameter(ProductsPreviewParameterProvider::class, limit = 1) item: ProductUi,
) {
    VkProductsTheme {
        ProductCard(
            id = item.id,
            thumbnail = item.thumbnail,
            priceWithDiscount = item.priceWithDiscount,
            price = item.price,
            discountPercentage = item.discountPercentage,
            title = item.title,
            description = item.description,
            rating = item.rating,
            stock = item.stock,
            onClick = { },
        )
    }
}