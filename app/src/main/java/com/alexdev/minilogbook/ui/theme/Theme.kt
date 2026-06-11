package com.alexdev.minilogbook.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Emerald500,
    onPrimary = Color.White,
    primaryContainer = Emerald900,
    onPrimaryContainer = Emerald100,
    secondary = Emerald500,
    onSecondary = Gray950,
    secondaryContainer = Emerald800,
    onSecondaryContainer = Emerald100,
    surface = Gray800,
    onSurface = Gray200,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,
    background = Gray900,
    onBackground = Gray200,
    outline = Gray700,
    outlineVariant = Gray800,
)

private val LightColorScheme = lightColorScheme(
    primary = Emerald500,
    onPrimary = Color.White,
    primaryContainer = Emerald100,
    onPrimaryContainer = Emerald600,
    secondary = Emerald600,
    onSecondary = Color.White,
    secondaryContainer = Emerald100,
    onSecondaryContainer = Emerald900,
    surface = Color.White,
    onSurface = Color(0xFF111827),
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = Color(0xFF6B7280),
    background = Color(0xFFF9FAFB),
    onBackground = Color(0xFF111827),
    outline = Color(0xFFD1D5DB),
    outlineVariant = Color(0xFFE5E7EB),
)

@Composable
fun MiniLogbookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
