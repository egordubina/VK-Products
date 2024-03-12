package ru.egordubina.vkproducts.ui.products.screens.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.products.ProductUi
import ru.egordubina.vkproducts.ui.products.utils.ProductsPreviewParameterProvider
import ru.egordubina.vkproducts.ui.theme.VkProductsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onBackButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            ProductDetailUiState.Error -> {}
            ProductDetailUiState.Loading -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            is ProductDetailUiState.Success -> ProductDetailSuccess(
                product = uiState.product,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview
@Composable
private fun ProductDetailPreview(
    @PreviewParameter(ProductsPreviewParameterProvider::class, limit = 1) product: ProductUi,
) {
    VkProductsTheme {
        ProductDetailScreen(uiState = ProductDetailUiState.Success(product = product)) {}
    }
}