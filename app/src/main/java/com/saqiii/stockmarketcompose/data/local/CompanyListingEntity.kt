package com.saqiii.stockmarketcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormatSymbols

@Entity
data class CompanyListingEntity(
    val name:String,
    val symbol: String,
    val exchange:String,
    @PrimaryKey val id:Int? = null
)
