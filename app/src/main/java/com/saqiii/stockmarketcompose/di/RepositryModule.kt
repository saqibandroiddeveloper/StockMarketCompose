package com.saqiii.stockmarketcompose.di

import com.saqiii.stockmarketcompose.data.csv.CSVParser
import com.saqiii.stockmarketcompose.data.csv.CompanyListingParser
import com.saqiii.stockmarketcompose.data.csv.IntraDayInfoParser
import com.saqiii.stockmarketcompose.data.repository.StockRepositoryImpl
import com.saqiii.stockmarketcompose.domain.model.CompanyListing
import com.saqiii.stockmarketcompose.domain.model.IntraDayInfo
import com.saqiii.stockmarketcompose.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(companyListingParser: CompanyListingParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntraDayParser(intraDayInfoParser: IntraDayInfoParser): CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

}