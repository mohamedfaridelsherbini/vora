package com.mohamedfaridelsherbini.vora.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TimeText
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.mohamedfaridelsherbini.vora.wear.R
import com.mohamedfaridelsherbini.vora.wear.presentation.theme.VoraTheme
import kotlinx.coroutines.delay

private const val WearSplashHandoffDurationMs = 420L

class WearEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(WearSplashHandoffDurationMs)
        showSplash = false
    }

    VoraTheme {
        Crossfade(
            targetState = showSplash,
            animationSpec = tween(durationMillis = 180),
            label = "wear-app-shell",
        ) { splashVisible ->
            if (splashVisible) {
                WearSplashScreen()
            } else {
                WearHomePlaceholder()
            }
        }
    }
}

@Composable
private fun WearSplashScreen() {
    val pulse = rememberInfiniteTransition(label = "wear-pulse")
    val dotAlpha by pulse.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "wear-dot-alpha",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.vora_auto_splash_mark),
            contentDescription = null,
            modifier = Modifier.size(width = 62.dp, height = 78.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "vora",
            color = Color(0xFFF9FAFB),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Canvas(modifier = Modifier.size(6.dp)) {
            drawCircle(color = Color(0xFF2563EB).copy(alpha = dotAlpha))
        }
    }
}

@Composable
private fun WearHomePlaceholder() {
    ScreenScaffold(
        timeText = { TimeText() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF111827)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "vora",
                color = Color(0xFFF9FAFB),
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Speak to capture",
                color = Color(0xFF9CA3AF),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp()
}

@Preview
@Composable
private fun WearSplashScreenPreview() {
    VoraTheme {
        WearSplashScreen()
    }
}
