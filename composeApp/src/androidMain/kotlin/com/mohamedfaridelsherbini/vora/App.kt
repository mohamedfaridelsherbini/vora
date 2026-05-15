package com.mohamedfaridelsherbini.vora

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfaridelsherbini.vora.presentation.theme.VoraColors
import com.mohamedfaridelsherbini.vora.presentation.theme.interFontFamily

@Composable
@Preview
fun App() {
    MaterialTheme {
        Surface {
            PlaceholderHome()
        }
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
    textColor: androidx.compose.ui.graphics.Color,
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
