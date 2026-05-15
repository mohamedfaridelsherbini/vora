package com.mohamedfaridelsherbini.vora.car.presentation.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.car.R

@Composable
internal fun CarSplashScreen(state: CarSplashVisualState) {
    val pulse = rememberInfiniteTransition(label = "car-voice-pulse")
    val pulseAlpha by pulse.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "car-dot-alpha",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.vora_auto_splash_mark),
                contentDescription = null,
                modifier = Modifier.size(width = 108.dp, height = 136.dp),
            )
            Text(
                text = "vora",
                color = state.brandColor,
                fontWeight = FontWeight.Bold,
                fontSize = 76.sp,
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        CarSplashDivider(state.dividerColor)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "VOICE MEMOS",
            color = state.mutedColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 1.8.sp,
        )
        Spacer(modifier = Modifier.height(26.dp))
        CarPulseDots(state.accentColor, listOf(pulseAlpha, pulseAlpha * 0.78f, pulseAlpha * 0.56f))
    }
}

@Composable
internal fun CarHomeScreen(state: CarHomeVisualState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(state.background)
            .padding(36.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Vora Car",
            color = state.titleColor,
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Playback-first automotive shell with shared contracts and DI composition.",
            color = state.bodyColor,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun CarSplashDivider(color: Color) {
    Canvas(modifier = Modifier.size(width = 120.dp, height = 4.dp)) {
        drawRoundRect(color = color, cornerRadius = CornerRadius(100f, 100f))
    }
}

@Composable
private fun CarPulseDots(
    color: Color,
    alphaValues: List<Float>,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        alphaValues.forEach { alpha ->
            Canvas(modifier = Modifier.size(8.dp)) {
                drawCircle(color = color.copy(alpha = alpha))
            }
        }
    }
}

@Preview(
    name = "Car Splash",
    widthDp = 1280,
    heightDp = 720,
    showBackground = true,
    backgroundColor = 0xFF111827,
)
@Composable
private fun CarSplashScreenPreview() {
    MaterialTheme {
        Surface {
            CarSplashScreen(state = CarSplashVisualState())
        }
    }
}

@Preview(
    name = "Car Home",
    widthDp = 1280,
    heightDp = 720,
    showBackground = true,
    backgroundColor = 0xFF111827,
)
@Composable
private fun CarHomeScreenPreview() {
    MaterialTheme {
        Surface {
            CarHomeScreen(state = CarHomeVisualState())
        }
    }
}
