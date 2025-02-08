package id.andriawan24.flappybirdclone.ui.presentation

import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.models.AreaSize
import id.andriawan24.flappybirdclone.ui.components.BirdPreview
import id.andriawan24.flappybirdclone.ui.components.GameBackground
import id.andriawan24.flappybirdclone.ui.components.GameForeground
import id.andriawan24.flappybirdclone.ui.components.GameForegroundPreview
import id.andriawan24.flappybirdclone.ui.components.PipePreview
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val state by mainViewModel.gameState.collectAsState()
    var screenSize: AreaSize by remember { mutableStateOf(AreaSize()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDDD896))
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // TODO: Handle click for start the game
                    }

                    else -> {
                        return@pointerInteropFilter false
                    }
                }

                true
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned {
                    screenSize = screenSize.copy(width = it.size.width, height = it.size.height)

                    if (state.playZoneSize.width <= 0 && state.playZoneSize.height <= 0) {
                        mainViewModel.onEvent(GameEvent.UpdateScreenSize, screenSize)
                    }
                }
                .fillMaxWidth()
                .weight(0.85f)
        ) {
            GameBackground(modifier = Modifier.fillMaxSize())
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .weight(0.15f)
        ) {
            GameForeground(
                modifier = Modifier.fillMaxSize(),
                state = state
            )
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    FlappyBirdCloneTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDDD896))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .weight(0.85f)
            ) {
                GameBackground(modifier = Modifier.fillMaxSize())
                PipePreview()
                BirdPreview()
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
                GameForegroundPreview()
            }
        }
    }
}
