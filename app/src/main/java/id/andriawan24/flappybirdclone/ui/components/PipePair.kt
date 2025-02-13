package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.models.LowPipe
import id.andriawan24.flappybirdclone.models.MiddlePipe
import id.andriawan24.flappybirdclone.models.PipeResetThreshold
import id.andriawan24.flappybirdclone.ui.presentation.MainViewModel
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameState
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@Composable
fun PipePair(modifier: Modifier = Modifier, state: GameState, pipeIndex: Int = 0) {
    val viewModel: MainViewModel = viewModel()
    val pipeState = state.pipeStateList[pipeIndex]

    Box(modifier = modifier) {
        Pipe(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = pipeState.offset),
            height = pipeState.topHeight,
            type = PipeType.TOP
        )

        Pipe(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = pipeState.offset),
            height = pipeState.bottomHeight,
            type = PipeType.BOTTOM
        )

        if (state.playZoneSize.width > 0) {
            val playZoneWidthInDp = with(LocalDensity.current) {
                state.playZoneSize.width.toDp()
            }

            if (pipeState.offset < -playZoneWidthInDp - PipeResetThreshold) {
                viewModel.onEvent(GameEvent.PipeExit, pipeIndex = pipeIndex)
            }
        }
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
private fun PipePairPreview() {
    FlappyBirdCloneTheme {
        PipePair(state = GameState())
    }
}
