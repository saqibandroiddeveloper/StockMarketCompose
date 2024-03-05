package com.saqiii.stockmarketcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Green,
    onPrimary = Color.DarkGray,
    background = DarkBlue,
    onBackground = TextWhite
)


/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/


@Composable
fun StockMarketComposeTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme, typography = Typography, content = content
    )
}