package ru.egordubina.vkproducts.ui.search

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.egordubina.ui.components.ProductCard
import ru.egordubina.vkproducts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    uiState: SearchUiState,
    onBackButtonAction: () -> Unit,
    onSearchButtonClick: (String) -> Unit,
    onSearchItemClick: (Int) -> Unit,
    loadData: (String, Int) -> Unit,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
                IconButton(onClick = {
                    searchQuery = ""
                    keyboardController?.show()
                }) {
                    Icon(imageVector = Icons.Rounded.Clear, contentDescription = null)
                }
            }
        },
        modifier = Modifier.focusRequester(focusRequester)
    ) {
        if (uiState.products.isNotEmpty())
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.small_padding))
            ) {
                itemsIndexed(uiState.products, key = { index, _ -> index }) { index, item ->
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
                        onClick = { onSearchItemClick(it) },
                    )
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
            LaunchedEffect(key1 = true) {
                scope.launch {
                    Toast.makeText(
                        context,
                        context.getString(R.string.label__error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    BackHandler { onBackButtonAction() }
}