package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.models.HighPipe
import id.andriawan24.flappybirdclone.models.LowPipe
import id.andriawan24.flappybirdclone.models.MiddlePipe
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

val PipeCapHeight = 30.dp
val PipeCapWidth = 60.dp

enum class PipeType {
    TOP, BOTTOM
}

@Composable
fun Pipe(modifier: Modifier = Modifier, height: Dp, type: PipeType) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(height)
    ) {
        Column {
            when (type) {
                PipeType.TOP -> {
                    PipePillar(
                        modifier = Modifier.align(CenterHorizontally),
                        height = height - 30.dp
                    )
                    PipeCap()
                }

                PipeType.BOTTOM -> {
                    PipeCap()
                    PipePillar(
                        modifier = Modifier.align(CenterHorizontally),
                        height = height - 30.dp
                    )
                }
            }
        }
    }
}

@Composable
fun PipeCap(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(width = PipeCapWidth, height = PipeCapHeight),
        painter = painterResource(R.drawable.pipe_cap),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}

@Composable
fun PipePillar(modifier: Modifier = Modifier, height: Dp = 120.dp) {
    Image(
        modifier = modifier.size(50.dp, height),
        painter = painterResource(R.drawable.pipe_pillar),
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun PipePreview() {
    FlappyBirdCloneTheme {
        Row(modifier = Modifier.wrapContentWidth()) {
            Spacer(modifier = Modifier.width(60.dp))

            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                PipeCap()
                Spacer(modifier = Modifier.height(48.dp))
                PipePillar(Modifier.align(CenterHorizontally))
                Spacer(modifier = Modifier.height(48.dp))
                PipeCap()
            }
            Spacer(modifier = Modifier.width(120.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Pipe(height = MiddlePipe, type = PipeType.TOP)
                Spacer(modifier = Modifier.height(180.dp))
                Pipe(height = LowPipe, type = PipeType.BOTTOM)
            }
            Spacer(modifier = Modifier.width(120.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Pipe(height = LowPipe, type = PipeType.TOP)
                Spacer(modifier = Modifier.height(180.dp))
                Pipe(height = MiddlePipe, type = PipeType.BOTTOM)
            }
            Spacer(modifier = Modifier.width(120.dp))
        }
    }
}
