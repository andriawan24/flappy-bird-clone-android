package id.andriawan24.flappybirdclone.ui.presentation

import android.os.SystemClock
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.andriawan24.flappybirdclone.MainApplication
import id.andriawan24.flappybirdclone.models.AreaSize
import id.andriawan24.flappybirdclone.models.BirdPipeDistanceFraction
import id.andriawan24.flappybirdclone.models.BirdSizeHeight
import id.andriawan24.flappybirdclone.models.BirdSizeWidth
import id.andriawan24.flappybirdclone.models.CenterPipeFraction
import id.andriawan24.flappybirdclone.models.HighPipe
import id.andriawan24.flappybirdclone.models.LowPipe
import id.andriawan24.flappybirdclone.models.MaxPipeFraction
import id.andriawan24.flappybirdclone.models.MiddlePipe
import id.andriawan24.flappybirdclone.models.MinPipeFraction
import id.andriawan24.flappybirdclone.models.PipeDistance
import id.andriawan24.flappybirdclone.models.PipeDistanceFraction
import id.andriawan24.flappybirdclone.models.TotalPipeHeight
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameState
import id.andriawan24.flappybirdclone.utils.DensityUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    fun onEvent(gameEvent: GameEvent, playZoneSize: AreaSize = AreaSize(), roadIndex: Int = -1) {
        if (playZoneSize.width > 0 && playZoneSize.height > 0) {
            gameState.value.playZoneSize = playZoneSize
        }

        if (roadIndex > -1) {
            gameState.value.targetRoadIndex = roadIndex
        }

        handleEvent(gameEvent, gameState.value)
    }

    private fun handleEvent(gameEvent: GameEvent, state: GameState) {
        viewModelScope.launch {
            _gameState.update {
                when (gameEvent) {
                    GameEvent.RoadExit -> {
                        val newRoadState = if (state.targetRoadIndex == 0) {
                            listOf(state.roadStates[0].reset(), state.roadStates[1])
                        } else {
                            listOf(state.roadStates[0], state.roadStates[1].reset())
                        }

                        state.copy(roadStates = newRoadState)
                    }

                    GameEvent.UpdateScreenSize -> {
                        val playZoneHeightInDp = DensityUtil.dxToDp(
                            MainApplication.getContext()!!.resources,
                            state.playZoneSize.height
                        )

                        TotalPipeHeight = playZoneHeightInDp.dp
                        HighPipe = TotalPipeHeight * MaxPipeFraction
                        MiddlePipe = TotalPipeHeight * CenterPipeFraction
                        LowPipe = TotalPipeHeight * MinPipeFraction
                        PipeDistance = TotalPipeHeight * PipeDistanceFraction

                        BirdSizeHeight = PipeDistance * BirdPipeDistanceFraction
                        BirdSizeWidth = BirdSizeHeight * 1.44f

                        // TODO: Handle bird and pipe size
                        state.copy()
                    }

                    GameEvent.AutoTick -> {
                        // Move road
                        val newRoadStates = listOf(state.roadStates[0].move(), state.roadStates[1].move())

                        state.copy(roadStates = newRoadStates)
                    }
                }
            }
        }
    }

    companion object {
        const val WORK_DURATION = 2000L
    }
}
