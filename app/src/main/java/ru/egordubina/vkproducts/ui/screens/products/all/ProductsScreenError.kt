package ru.egordubina.vkproducts.ui.screens.products.all

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ProductScreenError() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Error!")
    }
}