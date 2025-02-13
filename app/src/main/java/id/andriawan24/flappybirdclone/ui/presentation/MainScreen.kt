package id.andriawan24.flappybirdclone.ui.presentation

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.MainActivity
import id.andriawan24.flappybirdclone.models.AreaSize
import id.andriawan24.flappybirdclone.models.BirdSizeHeight
import id.andriawan24.flappybirdclone.models.BirdSizeWidth
import id.andriawan24.flappybirdclone.models.PipeState
import id.andriawan24.flappybirdclone.models.PipeStatus
import id.andriawan24.flappybirdclone.ui.components.Bird
import id.andriawan24.flappybirdclone.ui.components.BirdPreview
import id.andriawan24.flappybirdclone.ui.components.GameBackground
import id.andriawan24.flappybirdclone.ui.components.GameForeground
import id.andriawan24.flappybirdclone.ui.components.GameForegroundPreview
import id.andriawan24.flappybirdclone.ui.components.PipeCapWidth
import id.andriawan24.flappybirdclone.ui.components.PipePair
import id.andriawan24.flappybirdclone.ui.components.PipePreview
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameStatus
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val state by mainViewModel.gameState.collectAsStateWithLifecycle()
    var screenSize: AreaSize by remember { mutableStateOf(AreaSize()) }

    LaunchedEffect(state.gameStatus) {
        Log.d(MainActivity::class.simpleName, "Status 2: ${state.gameStatus}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDDD896))
            .run {
                pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            when (state.gameStatus) {
                                GameStatus.Waiting -> mainViewModel.onEvent(GameEvent.Start)
                                GameStatus.Running -> mainViewModel.onEvent(GameEvent.TouchLift)
                                else -> return@pointerInteropFilter false
                            }
                        }

                        else -> return@pointerInteropFilter false
                    }

                    false
                }
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned {
                    screenSize = screenSize.copy(width = it.size.width, height = it.size.height)
                    if (state.playZoneSize.width <= 0 || state.playZoneSize.height <= 0) {
                        mainViewModel.onEvent(GameEvent.UpdateScreenSize, screenSize)
                    }
                }
                .fillMaxWidth()
                .weight(0.85f)
        ) {
            GameBackground(modifier = Modifier.fillMaxSize())
            PipePair(modifier = Modifier.fillMaxSize(), state = state, pipeIndex = 0)
            PipePair(modifier = Modifier.fillMaxSize(), state = state, pipeIndex = 1)
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(24.dp),
                text = "Score: ${state.score}"
            )

            val playZoneWidthDp = with(LocalDensity.current) { state.playZoneSize.width.toDp() }
            val playZoneHeightDp = with(LocalDensity.current) { state.playZoneSize.height.toDp() }

            if (state.gameStatus == GameStatus.Running) {
                state.pipeStateList.forEachIndexed { index, pipeState ->
                    checkPipeStatus(
                        birdHeightOffset = state.birdState.birdHeight,
                        pipeState = pipeState,
                        zoneWidth = playZoneWidthDp,
                        zoneHeight = playZoneHeightDp
                    ).also {
                        when (it) {
                            PipeStatus.BirdHit -> mainViewModel.onEvent(GameEvent.HitPipe)
                            PipeStatus.BirdCrossed -> mainViewModel.onEvent(GameEvent.CrossedPipe, pipeIndex = index)
                            else -> {
                                // do nothing
                            }
                        }
                    }
                }
            } else if (state.gameStatus == GameStatus.Over) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        mainViewModel.onEvent(GameEvent.Restart)
                    }
                ) {
                    Text("Play Again")
                }
            }

            Bird(
                modifier = Modifier.fillMaxSize(),
                state = state
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .weight(0.15f)
        ) {
            GameForeground(
                modifier = Modifier.fillMaxSize(),
                state = state,
                mainViewModel = mainViewModel
            )
        }
    }
}

@Composable
fun checkPipeStatus(birdHeightOffset: Dp, pipeState: PipeState, zoneWidth: Dp, zoneHeight: Dp): PipeStatus {
    if (pipeState.offset - PipeCapWidth > -zoneWidth / 2 + BirdSizeWidth / 2) {
        return PipeStatus.BirdComing
    } else if (pipeState.offset - PipeCapWidth < -zoneWidth / 2 - BirdSizeWidth / 2) {
        return PipeStatus.BirdCrossed
    } else {
        val birdTop = (zoneHeight - BirdSizeHeight) / 2 + birdHeightOffset
        val birdBottom = (zoneHeight + BirdSizeHeight) / 2 + birdHeightOffset

        if (birdTop < pipeState.topHeight || birdBottom > zoneHeight - pipeState.bottomHeight) {
            return PipeStatus.BirdHit
        }

        return PipeStatus.BirdCrossing
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
