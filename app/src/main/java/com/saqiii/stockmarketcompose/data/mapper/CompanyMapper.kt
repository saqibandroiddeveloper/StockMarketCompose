package com.saqiii.stockmarketcompose.data.mapper

import com.saqiii.stockmarketcompose.data.local.CompanyListingEntity
import com.saqiii.stockmarketcompose.data.remote.dto.CompanyInfoDto
import com.saqiii.stockmarketcompose.domain.model.CompanyInfo
import com.saqiii.stockmarketcompose.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name, symbol, exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name, symbol, exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}