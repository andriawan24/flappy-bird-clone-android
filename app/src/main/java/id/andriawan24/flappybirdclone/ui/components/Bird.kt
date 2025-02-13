package id.andriawan24.flappybirdclone.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.models.BirdHitGroundThreshold
import id.andriawan24.flappybirdclone.models.BirdSizeHeight
import id.andriawan24.flappybirdclone.models.BirdSizeWidth
import id.andriawan24.flappybirdclone.models.DefaultBirdHeightOffset
import id.andriawan24.flappybirdclone.ui.presentation.MainViewModel
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameState
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

const val IdleDegree = 0f
const val LiftingDegree = -10f
const val FallingDegree = -LiftingDegree
const val DyingDegree = FallingDegree + 10f
const val DeadDegree = DyingDegree - 10f

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun Bird(modifier: Modifier = Modifier, state: GameState) {
    val viewModel: MainViewModel = viewModel()

    val rotation = when {
        state.isLifting -> LiftingDegree
        state.isFalling -> FallingDegree
        state.isQuickFalling -> DyingDegree
        state.isOver -> DeadDegree
        else -> IdleDegree
    }

    Box(modifier) {
        var birdHeight = state.birdState.birdHeight

        if (state.playZoneSize.height > 0) {
            val playZoneHeightInDp = with(LocalDensity.current) {
                state.playZoneSize.height.toDp()
            }

            val fallingThreshold = BirdHitGroundThreshold

            if (birdHeight + fallingThreshold >= playZoneHeightInDp / 2) {
                viewModel.onEvent(GameEvent.HitGround)
                birdHeight = playZoneHeightInDp / 2 - fallingThreshold
            }
        }

        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(state.birdState.birdW, state.birdState.birdH)
                .offset(y = birdHeight)
                .rotate(rotation),
            painter = painterResource(R.drawable.bird),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun BirdPreview() {
    FlappyBirdCloneTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .size(BirdSizeWidth, BirdSizeHeight)
                    .align(Alignment.Center)
                    .offset(x = 0.dp, y = DefaultBirdHeightOffset),
                painter = painterResource(R.drawable.bird),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    }
}
