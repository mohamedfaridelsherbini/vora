package com.mohamedfaridelsherbini.vora

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily

@Composable
internal fun VoraSplashContent(state: SplashVisualState) {
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
            .background(state.background)
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
        SplashDivider(color = state.dividerColor, width = 84.dp)
        Spacer(modifier = Modifier.height(14.dp))
        BrandWordmark("vora", state.titleColor, 36.sp, 40.sp)
        Spacer(modifier = Modifier.height(8.dp))
        BrandSupportingText("Voice memos, captured calmly.", state.subtitleColor, 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        PulseDots(VoraColors.LogoBlue, listOf(dotAlpha, dotAlpha * 0.8f, dotAlpha * 0.55f), 6.dp, 8.dp)
        Spacer(modifier = Modifier.height(12.dp))
        BrandSupportingText(
            "Voice pulse • 1.2s ease-in-out • reduced-motion: fade only",
            state.footerColor,
            11.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
internal fun VoraHomeContent(state: HomeVisualState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background)
            .safeContentPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Vora",
            color = state.titleColor,
            fontFamily = interFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        BrandSupportingText(
            "Android shell wired for Clean Architecture, DI, and shared use-case orchestration.",
            state.subtitleColor,
            14.sp,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FoundationPill("Shared graph", state.pillTextColor)
            FoundationPill("Feature-first UI", state.pillTextColor)
            FoundationPill("Native audio later", state.pillTextColor)
        }
    }
}

@Composable
private fun BrandWordmark(
    text: String,
    color: Color,
    fontSize: TextUnit,
    lineHeight: TextUnit = fontSize,
) {
    Text(
        text = text,
        color = color,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        lineHeight = lineHeight,
    )
}

@Composable
private fun BrandSupportingText(
    text: String,
    color: Color,
    fontSize: TextUnit,
) {
    Text(
        text = text,
        color = color,
        fontFamily = interFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun SplashDivider(
    color: Color,
    width: Dp,
) {
    Canvas(modifier = Modifier.size(width = width, height = 4.dp)) {
        drawRoundRect(color = color, cornerRadius = CornerRadius(100f, 100f))
    }
}

@Composable
private fun PulseDots(
    dotColor: Color,
    alphaValues: List<Float>,
    dotSize: Dp,
    spacing: Dp,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
        alphaValues.forEach { alpha ->
            Canvas(modifier = Modifier.size(dotSize)) {
                drawCircle(color = dotColor.copy(alpha = alpha))
            }
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
