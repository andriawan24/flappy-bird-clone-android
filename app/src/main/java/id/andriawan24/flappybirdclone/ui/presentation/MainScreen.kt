package id.andriawan24.flappybirdclone.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.R

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        GameBackground(modifier = Modifier.fillMaxSize())

        BoxWithConstraints(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            MainContent(
                screenWidth = maxWidth,
                screenHeight = maxHeight
            )
        }
    }
}

@Composable
fun GameBackground(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.img_background_day),
        contentDescription = "Background Image",
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    screenWidth: Dp,
    screenHeight: Dp
) {
    val pipeWidth = 80.dp

    LazyRow(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        item {
            Column(modifier = Modifier.width(pipeWidth)) {
                Pipe(height = (screenHeight.value).dp)
                Spacer(modifier = Modifier.weight(1f))
                Pipe(height = 200.dp)
            }
        }
    }
}

@Composable
fun Pipe(modifier: Modifier = Modifier, height: Dp) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(MaterialTheme.colorScheme.primary)
    )
}