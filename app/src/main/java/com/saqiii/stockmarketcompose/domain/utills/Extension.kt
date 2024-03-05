package com.saqiii.stockmarketcompose.domain.utills

fun String.toDefaultDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        0.0
    }
}