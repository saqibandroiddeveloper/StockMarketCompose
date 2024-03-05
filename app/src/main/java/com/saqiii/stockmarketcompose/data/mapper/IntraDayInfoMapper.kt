package com.saqiii.stockmarketcompose.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.saqiii.stockmarketcompose.data.remote.dto.IntraDayInfoDto
import com.saqiii.stockmarketcompose.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun IntraDayInfoDto.toIntraDayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfo(
        date = localDateTime ?: LocalDateTime.now(),
        close = close
    )
}