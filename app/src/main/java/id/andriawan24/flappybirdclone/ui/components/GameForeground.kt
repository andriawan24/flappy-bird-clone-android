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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.R
import id.andriawan24.flappybirdclone.ui.theme.FlappyBirdCloneTheme

@Composable
fun GameForeground(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HorizontalDivider(
            color = Color(0xFF523847),
            thickness = 5.dp
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.23f)
                    .offset(x = (-10).dp),
                painter = painterResource(R.drawable.foreground_road),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.23f)
                    .offset(x = 290.dp),
                painter = painterResource(id = R.drawable.foreground_road),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.77f),
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.foreground_earth_dark else R.drawable.foreground_earth),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
    }
}

@Preview(widthDp = 411, heightDp = 180, showBackground = true)
@Composable
private fun GameForegroundPreview() {
    FlappyBirdCloneTheme {
        GameForeground(modifier = Modifier.fillMaxSize())
    }
}
