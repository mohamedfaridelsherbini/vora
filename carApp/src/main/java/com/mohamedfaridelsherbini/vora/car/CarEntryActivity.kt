package com.mohamedfaridelsherbini.vora.car

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private const val SplashHandoffDurationMs = 480L

class CarEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CarApp()
        }
    }
}

@Composable
private fun CarApp() {
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
                label = "car-app-shell",
            ) { splashVisible ->
                if (splashVisible) {
                    CarSplashScreen()
                } else {
                    CarHomePlaceholder()
                }
            }
        }
    }
}

@Composable
private fun CarSplashScreen() {
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
            .background(Color(0xFF111827)),
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
                color = Color(0xFFF9FAFB),
                fontWeight = FontWeight.Bold,
                fontSize = 76.sp,
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        Canvas(modifier = Modifier.size(width = 120.dp, height = 4.dp)) {
            drawRoundRect(
                color = Color(0xFF24314F),
                cornerRadius = CornerRadius(100f, 100f),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "VOICE MEMOS",
            color = Color(0xFF9CA3AF),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 1.8.sp,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(3) { index ->
                val alpha = when (index) {
                    0 -> pulseAlpha
                    1 -> pulseAlpha * 0.78f
                    else -> pulseAlpha * 0.56f
                }
                Canvas(modifier = Modifier.size(8.dp)) {
                    drawCircle(color = Color(0xFF2563EB).copy(alpha = alpha))
                }
            }
        }
    }
}

@Composable
private fun CarHomePlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827))
            .padding(36.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Vora Car",
            color = Color(0xFFF9FAFB),
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Playback-first automotive shell with shared contracts and DI composition.",
            color = Color(0xFF9CA3AF),
            style = MaterialTheme.typography.bodyLarge,
        )
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
            CarSplashScreen()
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
private fun CarHomePlaceholderPreview() {
    MaterialTheme {
        Surface {
            CarHomePlaceholder()
        }
    }
}
