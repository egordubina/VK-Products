package ru.egordubina.vkproducts.ui.products.screens.detail

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
import ru.egordubina.vkproducts.ui.products.asUi
import javax.inject.Inject

@HiltViewModel
internal class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {
    private val id = checkNotNull(savedStateHandle["productId"]).toString().toInt()
    private val _uiState: MutableStateFlow<ProductDetailUiState> =
        MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        loadData()
    }

    fun refresh() {
        loadData()
    }

    private fun loadData() {
        job?.cancel()
        _uiState.update {
            it.copy(
                isLoading = true,
                isError = false
            )
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getProductsUseCase.getProductById(id)
                _uiState.update { it.copy(
                    product = response.asUi(),
                    isError = false
                ) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isError = true) }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}