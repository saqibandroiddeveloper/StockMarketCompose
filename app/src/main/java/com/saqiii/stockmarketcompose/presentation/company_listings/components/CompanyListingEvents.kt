package com.saqiii.stockmarketcompose.presentation.company_listings.components

sealed class CompanyListingEvents {
    data object Refresh : CompanyListingEvents()
    data class OnSearchQueryChange(val query:String):CompanyListingEvents()
}