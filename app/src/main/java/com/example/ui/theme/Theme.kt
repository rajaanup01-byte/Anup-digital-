package com.example.ui.theme

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
    primary = DarkThemePrimary,
    onPrimary = Color.Black,
    primaryContainer = GovBluePrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = DarkThemeSecondary,
    tertiary = SaffronLight ?: Color(0xFFFFB74D),
    background = DarkThemeBackground,
    surface = DarkThemeSurface,
    onBackground = DarkThemeText,
    onSurface = DarkThemeText,
    surfaceVariant = Color(0xFF273549),
    onSurfaceVariant = DarkThemeTextMuted
)

private val LightColorScheme = lightColorScheme(
    primary = GovBluePrimary,
    onPrimary = Color.White,
    primaryContainer = GovBlueContainer,
    onPrimaryContainer = OnGovBlueContainer,
    secondary = CyberSecondary,
    onSecondary = Color.White,
    secondaryContainer = CyberSecondaryContainer,
    tertiary = SaffronAccent,
    onTertiary = Color.White,
    tertiaryContainer = SaffronContainer,
    onTertiaryContainer = OnSaffronContainer,
    background = GovBackground,
    surface = GovSurface,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = GovSurfaceVariant,
    onSurfaceVariant = TextSecondaryMuted
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent branding identity
    content: @Composable () -> Unit
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
        content = content
    )
}
