package com.saqiii.stockmarketcompose.domain.repository

import com.saqiii.stockmarketcompose.domain.model.CompanyInfo
import com.saqiii.stockmarketcompose.domain.model.CompanyListing
import com.saqiii.stockmarketcompose.domain.model.IntraDayInfo
import com.saqiii.stockmarketcompose.domain.utills.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query: String
    ):Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol:String
    ):Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ):Resource<CompanyInfo>
}