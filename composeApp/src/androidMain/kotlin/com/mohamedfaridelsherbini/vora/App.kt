package com.mohamedfaridelsherbini.vora

import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily
import kotlinx.coroutines.delay

private const val SplashHandoffDurationMs = 420L

@Composable
@Preview
fun App() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(SplashHandoffDurationMs)
        showSplash = false
    }

    MaterialTheme {
        Surface {
            Crossfade(
                targetState = showSplash,
                animationSpec = tween(durationMillis = 220),
                label = "app-shell",
            ) { splashVisible ->
                if (splashVisible) {
                    VoraSplashScreen()
                } else {
                    PlaceholderHome()
                }
            }
        }
    }
}

@Composable
private fun VoraSplashScreen() {
    val dark = isSystemInDarkTheme()
    val background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper
    val titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk
    val subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.8f) else VoraColors.VoraMuted
    val lineColor = if (dark) Color(0xFF1A2B5E) else Color(0xFFD8E4FF)
    val footerColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.64f) else VoraColors.VoraMuted.copy(alpha = 0.84f)
    val fontFamily = interFontFamily()
    val pulse = rememberInfiniteTransition(label = "voice-pulse")
    val dotAlpha by pulse.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 950),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dot-alpha",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .safeContentPadding()
            .padding(horizontal = 32.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1.05f))
        Image(
            painter = painterResource(R.drawable.vora_auto_splash_mark),
            contentDescription = null,
            modifier = Modifier.size(width = 98.dp, height = 122.dp),
        )
        Spacer(modifier = Modifier.height(14.dp))
        Canvas(modifier = Modifier.size(width = 84.dp, height = 4.dp)) {
            drawRoundRect(
                color = lineColor,
                cornerRadius = CornerRadius(100f, 100f),
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "vora",
            color = titleColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 40.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Voice memos, captured calmly.",
            color = subtitleColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { index ->
                val alpha = when (index) {
                    0 -> dotAlpha
                    1 -> dotAlpha * 0.8f
                    else -> dotAlpha * 0.55f
                }
                Canvas(modifier = Modifier.size(6.dp)) {
                    drawCircle(color = VoraColors.LogoBlue.copy(alpha = alpha))
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Voice pulse • 1.2s ease-in-out • reduced-motion: fade only",
            color = footerColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
private fun PlaceholderHome() {
    val dark = isSystemInDarkTheme()
    val background = if (dark) VoraColors.LogoCharcoal else VoraColors.LogoPaper
    val titleColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk
    val subtitleColor = if (dark) VoraColors.VoraMuted.copy(alpha = 0.72f) else VoraColors.VoraMuted
    val pillTextColor = if (dark) VoraColors.LogoPaper else VoraColors.LogoInk
    val fontFamily = interFontFamily()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .safeContentPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Vora",
            color = titleColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Android shell wired for Clean Architecture, DI, and shared use-case orchestration.",
            color = subtitleColor,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FoundationPill("Shared graph", pillTextColor)
            FoundationPill("Feature-first UI", pillTextColor)
            FoundationPill("Native audio later", pillTextColor)
        }
    }
}

@Composable
private fun FoundationPill(
    label: String,
    textColor: Color,
) {
    Text(
        text = label,
        color = textColor,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        modifier = Modifier
            .background(
                color = VoraColors.LogoBlue.copy(alpha = 0.12f),
                shape = MaterialTheme.shapes.large,
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
    )
}
