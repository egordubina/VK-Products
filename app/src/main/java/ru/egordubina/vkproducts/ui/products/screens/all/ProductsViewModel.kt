package ru.egordubina.vkproducts.ui.products.screens.all

import androidx.lifecycle.SavedStateHandle
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
internal class ProductsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {
    private val category = checkNotNull(savedStateHandle["category"]).toString()
    private var _uiState: MutableStateFlow<ProductsUiState> =
        MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        loadData(1, CategoryType[category]?.query ?: "")
    }

    fun refresh() {
        loadData(1, CategoryType[category]?.query ?: "")
    }

    fun loadNextPage(page: Int, category: String) {
        job?.cancel()
        _uiState.update {
            it.copy(selectedCategory = CategoryType[category] ?: CategoryType.ALL)
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductsUseCase.getAllProducts(page, category)
                val data = response.map { it.asUi() }
                _uiState.update { it.copy(products = it.products + data) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isError = true) }
            }
        }
    }

    private fun loadData(page: Int, category: String) {
        job?.cancel()
        _uiState.update {
            it.copy(
                isLoading = true,
                selectedCategory = CategoryType[category] ?: CategoryType.ALL
            )
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductsUseCase.getAllProducts(page, category)
                val productsUi = response.map { it.asUi() }
                _uiState.update {
                    it.copy(
                        products = productsUi,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isError = true) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}