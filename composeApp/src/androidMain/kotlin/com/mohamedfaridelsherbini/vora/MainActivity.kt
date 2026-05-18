package com.mohamedfaridelsherbini.vora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val notesFeatureService: NotesFeatureService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(notesFeatureService = notesFeatureService)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppPreview()
}
