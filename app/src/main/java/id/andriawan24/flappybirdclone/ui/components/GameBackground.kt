package id.andriawan24.flappybirdclone.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import id.andriawan24.flappybirdclone.R

@Composable
fun GameBackground(modifier: Modifier = Modifier) {
    val darkTheme = isSystemInDarkTheme()

    Image(
        modifier = modifier,
        painter = painterResource(id = if (darkTheme) R.drawable.img_background_night else R.drawable.img_background_day),
        contentDescription = "Background Image",
        contentScale = ContentScale.FillBounds
    )
}