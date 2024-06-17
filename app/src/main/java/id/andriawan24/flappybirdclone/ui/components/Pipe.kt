package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@Composable
fun PipeDown(screenHeight: Dp) {
    Image(
        modifier = Modifier
            .rotate(180f)
            .width(60.dp)
            .height(screenHeight / 2),
        contentScale = ContentScale.FillBounds,
        painter = painterResource(R.drawable.img_pipe_green),
        contentDescription = stringResource(R.string.description_pipe_down)
    )
}

@Composable
fun PipeUp() {

}

@Preview(showBackground = true, device = Devices.PIXEL_2, showSystemUi = true)
@Composable
private fun PipeDownPreview() {
    FlappyBirdCloneTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints {
                PipeDown(screenHeight = maxHeight)
            }
        }
    }
}