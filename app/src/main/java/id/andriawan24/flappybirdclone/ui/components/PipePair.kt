package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.andriawan24.flappybirdclone.models.LowPipe
import id.andriawan24.flappybirdclone.models.MiddlePipe
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@Composable
fun PipePair(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Pipe(
            modifier = Modifier.align(Alignment.TopEnd),
            height = MiddlePipe,
            type = PipeType.TOP
        )

        Pipe(
            modifier = Modifier.align(Alignment.BottomEnd),
            height = LowPipe,
            type = PipeType.BOTTOM
        )
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
private fun PipePairPreview() {
    FlappyBirdCloneTheme {
        PipePair()
    }
}
