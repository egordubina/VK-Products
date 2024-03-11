package ru.egordubina.vkproducts.ui.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.egordubina.vkproducts.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    selectedCategory: CategoryType,
    onCategoryClick: (CategoryType) -> Unit,
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(stringResource(id = R.string.filters)) }) }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.consumeWindowInsets(innerPadding)
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
fun CategoryItem(
    category: CategoryType,
    isSelected: Boolean,
    onClick: (CategoryType) -> Unit,
) {
    if (isSelected)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick(category) }
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
            Text(
                text = stringResource(id = category.title),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    else
        Text(
            text = stringResource(id = category.title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onClick(category) }
                .padding(16.dp)
        )
}