package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.ui.presentation.MainViewModel
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameState
import id.andriawan24.flappybirdclone.ui.state.TempRoadWidthOffset
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@Composable
fun GameForeground(modifier: Modifier = Modifier, state: GameState) {
    val viewModel: MainViewModel = viewModel()

    Column(modifier = modifier) {
        HorizontalDivider(
            color = Color(0xFF523847),
            thickness = 5.dp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.23f)
        ) {
            state.roadStates.forEach { roadState ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .offset(x = roadState.offset),
                    painter = painterResource(R.drawable.foreground_road),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.77f)
                .weight(0.77f),
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.foreground_earth_dark else R.drawable.foreground_earth),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        if (state.playZoneSize.width > 0) {
            state.roadStates.forEachIndexed { index, roadState ->
                if (roadState.offset <= -TempRoadWidthOffset) {
                    viewModel.onEvent(GameEvent.RoadExit, roadIndex = index)
                }
            }
        }
    }
}

@Preview(widthDp = 411, heightDp = 180, showBackground = true)
@Composable
fun GameForegroundPreview() {
    FlappyBirdCloneTheme {
        Column(modifier = Modifier) {
            HorizontalDivider(
                color = Color(0xFF523847),
                thickness = 5.dp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.23f)
            ) {
//                state.roadStates.forEach { roadState ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(R.drawable.foreground_road),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
//                }
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.77f)
                    .weight(0.77f),
                painter = painterResource(if (isSystemInDarkTheme()) R.drawable.foreground_earth_dark else R.drawable.foreground_earth),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
    }
}
