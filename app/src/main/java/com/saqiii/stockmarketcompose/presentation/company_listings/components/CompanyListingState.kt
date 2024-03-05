package com.saqiii.stockmarketcompose.presentation.company_listings.components

import com.saqiii.stockmarketcompose.domain.model.CompanyListing

data class CompanyListingState(
    val companies:List<CompanyListing> = emptyList(),
    val isLoading:Boolean = false,
    val isRefreshing:Boolean = false,
    val searchQuery:String = ""
)
