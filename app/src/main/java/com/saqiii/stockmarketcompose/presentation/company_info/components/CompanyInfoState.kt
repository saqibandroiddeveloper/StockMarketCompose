package com.saqiii.stockmarketcompose.presentation.company_info.components

import com.saqiii.stockmarketcompose.domain.model.CompanyInfo
import com.saqiii.stockmarketcompose.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfo:List<IntraDayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error:String? = null
)
