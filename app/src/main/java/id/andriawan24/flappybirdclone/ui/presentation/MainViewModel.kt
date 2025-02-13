package id.andriawan24.flappybirdclone.ui.presentation

import android.os.SystemClock
import android.util.Log
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
import id.andriawan24.flappybirdclone.models.PipeState
import id.andriawan24.flappybirdclone.models.TotalPipeHeight
import id.andriawan24.flappybirdclone.ui.state.GameEvent
import id.andriawan24.flappybirdclone.ui.state.GameState
import id.andriawan24.flappybirdclone.ui.state.GameStatus
import id.andriawan24.flappybirdclone.utils.DensityUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val initTime = SystemClock.uptimeMillis()
    fun isDataReady() = SystemClock.uptimeMillis() - initTime > WORK_DURATION

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    fun onEvent(gameEvent: GameEvent, playZoneSize: AreaSize = AreaSize(), roadIndex: Int = -1, pipeIndex: Int = -1) {
        if (playZoneSize.width > 0 && playZoneSize.height > 0) {
            gameState.value.playZoneSize = playZoneSize
        }

        if (roadIndex > -1) {
            gameState.value.targetRoadIndex = roadIndex
        }

        if (pipeIndex > -1) {
            gameState.value.targetPipeIndex = pipeIndex
        }

        handleEvent(gameEvent, gameState.value)
    }

    private fun handleEvent(gameEvent: GameEvent, state: GameState) {
        viewModelScope.launch {
            _gameState.value = when (gameEvent) {
                GameEvent.Start -> run {
                    state.copy(gameStatus = GameStatus.Running)
                }

                GameEvent.AutoTick -> run {
                    Log.d(MainViewModel::class.simpleName, "Status 4: AutoTick ${state.gameStatus}")
                    // Do nothing if the game not started
                    if (state.gameStatus == GameStatus.Waiting) return@run state

                    // Quick fall when dying
                    if (state.gameStatus == GameStatus.Dying) {
                        val newBirdState = state.birdState.quickFall()
                        return@run state.copy(birdState = newBirdState)
                    }

                    if (state.gameStatus == GameStatus.Over) {
                        return@run state.copy()
                    }

                    // Move pipes left
                    val newPipeList: List<PipeState> = listOf(
                        state.pipeStateList[0].move(),
                        state.pipeStateList[1].move()
                    )

                    // Bird fall
                    val newBirdState = state.birdState.fall()

                    // Move road
                    val newRoadStates = listOf(state.roadStates[0].move(), state.roadStates[1].move())

                    Log.d(MainViewModel::class.simpleName, "Status 3: $newBirdState")

                    state.copy(
                        roadStates = newRoadStates,
                        birdState = newBirdState,
                        pipeStateList = newPipeList,
                        gameStatus = GameStatus.Running
                    )
                }

                GameEvent.TouchLift -> run {
                    if (state.gameStatus == GameStatus.Dying) {
                        return@run state.copy()
                    }

                    if (state.gameStatus == GameStatus.Over) {
                        return@run state.copy()
                    }

                    val newBirdState = state.birdState.lift()

                    state.copy(birdState = newBirdState)
                }

                GameEvent.PipeExit -> run {
                    val newPipeListState: List<PipeState> =
                        if (state.targetPipeIndex == 0) {
                            listOf(state.pipeStateList[0].reset(), state.pipeStateList[1])
                        } else {
                            listOf(state.pipeStateList[0], state.pipeStateList[1].reset())
                        }

                    state.copy(pipeStateList = newPipeListState, gameStatus = GameStatus.Running)
                }

                GameEvent.RoadExit -> run {
                    val newRoadState = if (state.targetRoadIndex == 0) {
                        listOf(state.roadStates[0].reset(), state.roadStates[1])
                    } else {
                        listOf(state.roadStates[0], state.roadStates[1].reset())
                    }

                    state.copy(roadStates = newRoadState)
                }

                GameEvent.HitGround -> run {
                    state.copy(gameStatus = GameStatus.Over)
                }

                GameEvent.HitPipe -> run {
                    if (state.gameStatus == GameStatus.Dying) {
                        return@run state.copy()
                    }

                    val newBirdState = state.birdState.quickFall()

                    state.copy(
                        birdState = newBirdState,
                        gameStatus = GameStatus.Dying
                    )
                }

                GameEvent.CrossedPipe -> run {
                    val targetPipeState = state.pipeStateList[state.targetPipeIndex]

                    if (targetPipeState.counted || targetPipeState.offset > 0.dp) {
                        return@run state.copy()
                    }

                    val countedPipeState = targetPipeState.count()
                    val newPipeStateList = if (state.targetPipeIndex == 0) {
                        listOf(countedPipeState, state.pipeStateList[1])
                    } else {
                        listOf(state.pipeStateList[0], countedPipeState)
                    }

                    state.copy(
                        pipeStateList = newPipeStateList,
                        score = state.score + 1,
                        bestScore = (state.score + 1).coerceAtLeast(state.bestScore)
                    )
                }

                GameEvent.Restart -> run {
                    state.reset(state.bestScore)
                }

                GameEvent.UpdateScreenSize -> {
                    val playZoneHeightInDp = DensityUtil.dxToDp(
                        MainApplication.getContext()!!.resources,
                        state.playZoneSize.height
                    )

                    // Pipe
                    TotalPipeHeight = playZoneHeightInDp.dp
                    HighPipe = TotalPipeHeight * MaxPipeFraction
                    MiddlePipe = TotalPipeHeight * CenterPipeFraction
                    LowPipe = TotalPipeHeight * MinPipeFraction
                    PipeDistance = TotalPipeHeight * PipeDistanceFraction

                    // Bird
                    BirdSizeHeight = PipeDistance * BirdPipeDistanceFraction
                    BirdSizeWidth = BirdSizeHeight * 1.44f

                    val newPipeListState = listOf(
                        state.pipeStateList[0].correct(),
                        state.pipeStateList[1].correct()
                    )

                    val newBirdState = state.birdState.correct()

                    state.copy(birdState = newBirdState, pipeStateList = newPipeListState)
                }
            }
        }
    }

    companion object {
        const val WORK_DURATION = 2000L
    }
}
