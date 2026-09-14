package com.dsavisualizer.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Semantic Theme Colors matching colors.xml & values-night/colors.xml
val Slate950 = Color(0xFF020617)
val Slate900 = Color(0xFF0F172A)
val Slate800 = Color(0xFF1E293B)
val Slate700 = Color(0xFF334155)
val Slate600 = Color(0xFF475569)
val Slate500 = Color(0xFF64748B)
val Slate400 = Color(0xFF94A3B8)
val Slate300 = Color(0xFFCBD5E1)
val Slate200 = Color(0xFFE2E8F0)
val Slate100 = Color(0xFFF1F5F9)
val Slate50 = Color(0xFFF8FAFC)

val CyberSky = Color(0xFF38BDF8)
val CyberSkyDark = Color(0xFF0284C7)
val CyberEmerald = Color(0xFF34D399)
val CyberEmeraldDark = Color(0xFF059669)
val CyberAmber = Color(0xFFF59E0B)
val CyberRose = Color(0xFFF43F5E)
val CyberViolet = Color(0xFFA78BFA)

val DarkWindowBg = Color(0xFF030712)
val DarkSurfaceCard = Color(0xFF0B132B)
val DarkSurfacePanel = Color(0xFF0A1124)
val DarkSurfaceStroke = Color(0xFF1E2E4A)

val LightWindowBg = Color(0xFFF8FAFC)
val LightSurfaceCard = Color(0xFFFFFFFF)
val LightSurfacePanel = Color(0xFFFFFFFF)
val LightSurfaceStroke = Color(0xFFE2E8F0)

@Immutable
data class DsaCustomColors(
    val cyberSky: Color,
    val cyberEmerald: Color,
    val cyberAmber: Color,
    val cyberRose: Color,
    val cyberViolet: Color,
    val surfaceStroke: Color,
    val nodeLocked: Color,
    val nodeAvailable: Color,
    val nodeCompleted: Color
)

val LocalDsaCustomColors = staticCompositionLocalOf {
    DsaCustomColors(
        cyberSky = CyberSky,
        cyberEmerald = CyberEmerald,
        cyberAmber = CyberAmber,
        cyberRose = CyberRose,
        cyberViolet = CyberViolet,
        surfaceStroke = DarkSurfaceStroke,
        nodeLocked = Slate600,
        nodeAvailable = CyberSky,
        nodeCompleted = CyberEmerald
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = CyberSky,
    onPrimary = Slate950,
    primaryContainer = CyberSkyDark,
    onPrimaryContainer = Slate50,
    secondary = CyberEmerald,
    onSecondary = Slate950,
    tertiary = CyberViolet,
    background = DarkWindowBg,
    onBackground = Slate50,
    surface = DarkSurfaceCard,
    onSurface = Slate50,
    surfaceVariant = DarkSurfacePanel,
    onSurfaceVariant = Slate300,
    outline = DarkSurfaceStroke
)

private val LightColorScheme = lightColorScheme(
    primary = CyberSkyDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEFF6FF),
    onPrimaryContainer = Slate900,
    secondary = CyberEmeraldDark,
    onSecondary = Color.White,
    tertiary = Color(0xFF7C3AED),
    background = LightWindowBg,
    onBackground = Slate900,
    surface = LightSurfaceCard,
    onSurface = Slate900,
    surfaceVariant = LightSurfacePanel,
    onSurfaceVariant = Slate700,
    outline = LightSurfaceStroke
)

@Composable
fun DsaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val customColors = DsaCustomColors(
        cyberSky = if (darkTheme) CyberSky else CyberSkyDark,
        cyberEmerald = if (darkTheme) CyberEmerald else CyberEmeraldDark,
        cyberAmber = CyberAmber,
        cyberRose = CyberRose,
        cyberViolet = if (darkTheme) CyberViolet else Color(0xFF7C3AED),
        surfaceStroke = if (darkTheme) DarkSurfaceStroke else LightSurfaceStroke,
        nodeLocked = if (darkTheme) Slate700 else Slate400,
        nodeAvailable = if (darkTheme) CyberSky else CyberSkyDark,
        nodeCompleted = if (darkTheme) CyberEmerald else CyberEmeraldDark
    )

    CompositionLocalProvider(LocalDsaCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
