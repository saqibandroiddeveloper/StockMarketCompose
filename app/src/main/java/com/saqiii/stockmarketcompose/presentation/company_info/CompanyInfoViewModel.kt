package com.saqiii.stockmarketcompose.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saqiii.stockmarketcompose.domain.repository.StockRepository
import com.saqiii.stockmarketcompose.domain.utills.Resource
import com.saqiii.stockmarketcompose.presentation.company_info.components.CompanyInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(
                isLoading = true
            )
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intraDayInfoResult = async { repository.getIntraDayInfo(symbol) }
            when (val result = companyInfoResult.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,
                        isLoading = false,
                        companyInfo = null
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        companyInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                else -> Unit
            }
            when (val result = intraDayInfoResult.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        error = result.message,
                        isLoading = false,
                        companyInfo = null
                    )
                }
                is Resource.Success -> {
                    state = state.copy(
                        stockInfo = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                else -> Unit
            }
        }

    }

}