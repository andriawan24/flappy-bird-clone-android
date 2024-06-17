package id.andriawan24.flappybirdclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.andriawan24.flappybirdclone.ui.presentation.MainScreen
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme by remember { mutableStateOf(false) }
            FlappyBirdCloneTheme(darkTheme = darkTheme) {
                MainScreen()
            }
        }
    }
}