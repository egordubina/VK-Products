package ru.egordubina.vkproducts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.egordubina.vkproducts.ui.navigation.VkProductsNavHost
import ru.egordubina.ui.theme.VkProductsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkProductsTheme {
                val navController = rememberNavController()
                VkProductsNavHost(navController = navController)
            }
        }
    }
}
