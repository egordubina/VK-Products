package ru.egordubina.vkproducts.ui.products.screens.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.egordubina.products.usecases.GetProductsUseCase
import ru.egordubina.vkproducts.ui.categories.CategoryType
import ru.egordubina.vkproducts.ui.products.asUi
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
        loadData(1, CategoryType.ALL.query)
    }

    fun refresh() {
        loadData(1, CategoryType.ALL.query)
    }

    fun loadNextPage(page: Int, category: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductsUseCase.getAllProducts(page, category)
                val data = response.map { it.asUi() }
                val products = when (val state = _uiState.value) {
                    is ProductsUiState.Success -> state.products + data
                    else -> data
                }
                _uiState.update {
                    ProductsUiState.Success(products)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { ProductsUiState.Error }
            }
        }
    }

    private fun loadData(page: Int, category: String) {
        job?.cancel()
        _uiState.value = ProductsUiState.Loading
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductsUseCase.getAllProducts(page, category)
                val productsUi = response.map { it.asUi() }
                _uiState.update {
                    ProductsUiState.Success(products = productsUi)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { ProductsUiState.Error }
            }
        }
    }
}