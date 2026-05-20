package com.mohamedfaridelsherbini.vora.presentation.splash

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.R
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraSpacing
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.provider.Settings
import com.mohamedfaridelsherbini.vora.presentation.theme.voraTypography

@Composable
internal fun VoraSplashScreen() {
    VoraSplashContent(state = splashVisualState())
}

@Composable
private fun VoraSplashContent(state: SplashVisualState) {
    val context = LocalContext.current
    val isReducedMotion = remember(context) {
        try {
            val animationScale = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.TRANSITION_ANIMATION_SCALE,
                1.0f
            )
            animationScale == 0.0f
        } catch (e: Exception) {
            false
        }
    }

    val pulse = rememberInfiniteTransition(label = "voice-pulse")
    val animatedAlpha by pulse.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 950),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "dot-alpha",
    )
    val dotAlpha = if (isReducedMotion) 1f else animatedAlpha

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background)
            .safeContentPadding()
            .padding(horizontal = VoraSpacing.SplashHorizontal, vertical = VoraSpacing.SplashVertical),
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
        val footerText = if (isReducedMotion) {
            "Voice pulse • reduced-motion: fade only"
        } else {
            "Voice pulse • 1.2s ease-in-out"
        }
        BrandSupportingText(
            footerText,
            state.footerColor,
            11.sp,
        )
        Spacer(modifier = Modifier.height(6.dp))
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
        style = voraTypography().displaySmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            lineHeight = lineHeight,
        ),
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
        style = voraTypography().bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
        ),
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

@Preview(name = "Phone Splash", showBackground = true, backgroundColor = 0xFFF9FAFB)
@Composable
private fun VoraSplashContentPreview() {
    MaterialTheme {
        VoraSplashScreen()
    }
}
