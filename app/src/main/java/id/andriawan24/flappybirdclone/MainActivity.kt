package id.andriawan24.flappybirdclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.andriawan24.flappybirdclone.ui.presentation.MainScreen
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlappyBirdCloneTheme {
                MainScreen()
            }
        }
    }
}