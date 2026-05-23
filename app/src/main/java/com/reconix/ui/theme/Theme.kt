package com.reconix.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ReconixDarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = DeepBlack,
    primaryContainer = CardDarkElevated,
    onPrimaryContainer = ElectricBlue,
    secondary = NeonPurple,
    onSecondary = DeepBlack,
    secondaryContainer = CardDark,
    onSecondaryContainer = NeonPurple,
    tertiary = CyanHighlight,
    onTertiary = DeepBlack,
    background = DeepBlack,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = CardDark,
    onSurfaceVariant = TextSecondary,
    outline = TextMuted,
    error = CriticalRed,
    onError = DeepBlack
)

@Composable
fun ReconixTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ReconixDarkColorScheme,
        typography = ReconixTypography,
        content = content
    )
}
