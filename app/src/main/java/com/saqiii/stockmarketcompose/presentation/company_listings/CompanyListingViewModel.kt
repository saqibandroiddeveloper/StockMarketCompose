package com.saqiii.stockmarketcompose.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saqiii.stockmarketcompose.domain.repository.StockRepository
import com.saqiii.stockmarketcompose.domain.utills.Resource
import com.saqiii.stockmarketcompose.presentation.company_listings.components.CompanyListingEvents
import com.saqiii.stockmarketcompose.presentation.company_listings.components.CompanyListingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    var state by mutableStateOf(CompanyListingState())
    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }


    fun onEvent(events: CompanyListingEvents) {
        when (events) {
            is CompanyListingEvents.OnSearchQueryChange -> {
                state = state.copy(
                    searchQuery = events.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }

            CompanyListingEvents.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let { listing ->
                            state = state.copy(
                                companies = listing
                            )
                        }
                    }
                }
            }
        }
    }
}