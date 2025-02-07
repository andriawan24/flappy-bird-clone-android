package id.andriawan24.flappybirdclone.ui.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.andriawan24.flappybirdclone.ui.components.GameBackground

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        GameBackground(modifier = Modifier.fillMaxSize())
//        BoxWithConstraints(
//            modifier = Modifier
//                .statusBarsPadding()
//                .navigationBarsPadding()
//        ) {
//            // TODO: Create main playground
//        }
    }
}
