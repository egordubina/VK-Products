package ru.egordubina.vkproducts.ui.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.products.usecases.GetProductsUseCase
import ru.egordubina.vkproducts.ui.screens.products.all.ProductsUiState
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<ProductsUiState> =
        MutableStateFlow(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        loadData()
    }

    fun refresh() {
        loadData()
    }

    private fun loadData() {
        job?.cancel()
        _uiState.value = ProductsUiState.Loading
        job = viewModelScope.launch {
            try {
                val products = getProductsUseCase.getAllProducts().map { it.asUi() }
                _uiState.update { ProductsUiState.Success(products) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { ProductsUiState.Error }
            }
        }
    }
}