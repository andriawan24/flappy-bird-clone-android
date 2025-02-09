package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.models.BirdSizeHeight
import id.andriawan24.flappybirdclone.models.BirdSizeWidth
import id.andriawan24.flappybirdclone.models.DefaultBirdHeightOffset
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

const val IdleDegree = 0f
const val LiftingDegree = -10f
const val FallingDegree = -LiftingDegree
const val DyingDegree = FallingDegree + 10f
const val DeadDegree = DyingDegree - 10f

@Composable
fun Bird(modifier: Modifier = Modifier) {
    val rotation by remember { mutableFloatStateOf(IdleDegree) }

    Box(modifier) {
        Image(
            modifier = modifier
                .size(BirdSizeWidth, BirdSizeHeight)
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
