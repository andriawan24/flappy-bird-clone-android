package id.andriawan24.flappybirdclone

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.ui.presentation.MainScreen
import id.andriawan24.flappybirdclone.ui.presentation.MainViewModel
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameStatus
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme
import id.andriawan24.flappybirdclone.utils.findRootView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

const val AutoTickDuration = 50L

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide system UI
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, findRootView()).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val darkTheme by remember { mutableStateOf(false) }

            FlappyBirdCloneTheme(darkTheme = darkTheme) {
                val mainViewModel: MainViewModel = viewModel()

                LaunchedEffect(true) {
                    while (isActive) {
                        delay(AutoTickDuration)
                        if (mainViewModel.gameState.value.gameStatus != GameStatus.Waiting) {
                            mainViewModel.onEvent(GameEvent.AutoTick)
                        }
                    }
                }

                MainScreen()
            }
        }
    }
}
