package ru.egordubina.vkproducts.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.egordubina.vkproducts.R
import ru.egordubina.vkproducts.ui.theme.VkProductsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoriesScreen(
    selectedCategory: CategoryType,
    onCategoryClick: (CategoryType) -> Unit,
    onBackButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.filters)) },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = innerPadding,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.consumeWindowInsets(innerPadding).padding(horizontal = 16.dp),
        ) {
            items(CategoryType.entries) {
                CategoryItem(
                    category = it,
                    isSelected = selectedCategory == it
                ) { onCategoryClick(it) }
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryType,
    isSelected: Boolean,
    onClick: (CategoryType) -> Unit,
) {
    Text(
        text = stringResource(id = category.title),
        style = MaterialTheme.typography.titleSmall,
        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onClick(category) }
            .padding(16.dp)
            .aspectRatio(1f)
    )
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    VkProductsTheme {
        CategoriesScreen(
            onBackButtonClick = {},
            onCategoryClick = {},
            selectedCategory = CategoryType.SMARTPHONES
        )
    }
}