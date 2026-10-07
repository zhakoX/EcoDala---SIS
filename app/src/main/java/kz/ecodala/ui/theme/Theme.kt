package kz.ecodala.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EcoDalaDarkColorScheme = darkColorScheme(
    primary = EcoGreenDarkTheme,
    onPrimary = EcoGreenDark,
    primaryContainer = EcoGreenDarkThemeContainer,
    onPrimaryContainer = EcoGreenLightDarkTheme,

    secondary = EcoGreenLightDarkTheme,
    onSecondary = EcoGreenDark,
    secondaryContainer = EcoSecondaryContainerDark,
    onSecondaryContainer = EcoGreenLightDarkTheme,

    tertiary = EcoGreenLightDarkTheme,
    onTertiary = EcoGreenDark,

    background = EcoBackgroundDark,
    onBackground = EcoOnSurfaceDark,

    surface = EcoSurfaceDark,
    onSurface = EcoOnSurfaceDark,

    surfaceVariant = EcoSurfaceVariantDark,
    onSurfaceVariant = EcoOnSurfaceVariantDark,

    surfaceContainerLowest = EcoBackgroundDark,
    surfaceContainerLow = EcoSurfaceDark,
    surfaceContainer = EcoSurfaceVariantDark,
    surfaceContainerHigh = EcoSurfaceVariantDark,
    surfaceContainerHighest = EcoSurfaceVariantDark
)

private val EcoDalaLightColorScheme = lightColorScheme(
    primary = EcoGreen,
    onPrimary = EcoSurfaceLight,
    primaryContainer = EcoGreenLight,
    onPrimaryContainer = EcoGreenDark,

    secondary = EcoGreenLight,
    onSecondary = EcoGreenDark,
    secondaryContainer = EcoSecondaryContainerLight,
    onSecondaryContainer = EcoGreenDark,

    tertiary = EcoGreenDark,
    onTertiary = EcoSurfaceLight,

    background = EcoBackgroundLight,
    onBackground = EcoOnSurfaceLight,

    surface = EcoSurfaceLight,
    onSurface = EcoOnSurfaceLight,

    surfaceVariant = EcoSurfaceVariantLight,
    onSurfaceVariant = EcoOnSurfaceVariantLight,

    surfaceContainerLowest = EcoSurfaceLight,
    surfaceContainerLow = EcoBackgroundLight,
    surfaceContainer = EcoSurfaceVariantLight,
    surfaceContainerHigh = EcoSurfaceVariantLight,
    surfaceContainerHighest = EcoSurfaceVariantLight
)

@Composable
fun EcoDalaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        EcoDalaDarkColorScheme
    } else {
        EcoDalaLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}