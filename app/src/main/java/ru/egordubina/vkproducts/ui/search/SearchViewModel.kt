package ru.egordubina.vkproducts.ui.search

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
import ru.egordubina.products.usecases.SearchProductsUseCase
import ru.egordubina.vkproducts.ui.products.asUi
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    fun loadNextPage(query: String, page: Int) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = searchProductsUseCase.searchProducts(query, page)
                val data = response.map { it.asUi() }
                _uiState.update { it.copy(products = it.products + data) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isError = true) }
            }
        }
    }

    fun searchProducts(query: String, page: Int) {
        job?.cancel()
        _uiState.update { it.copy(isLoading = true, isError = false) }
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = searchProductsUseCase
                    .searchProducts(query = query, page = page).map { it.asUi() }
                _uiState.update { it.copy(products = response) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isError = true) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}