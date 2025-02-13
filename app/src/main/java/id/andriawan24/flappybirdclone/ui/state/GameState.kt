package id.andriawan24.flappybirdclone.ui.state

import id.andriawan24.flappybirdclone.models.AreaSize
import id.andriawan24.flappybirdclone.models.BirdState
import id.andriawan24.flappybirdclone.models.PipeState
import id.andriawan24.flappybirdclone.models.PipeStateList

data class GameState(
    val gameStatus: GameStatus = GameStatus.Waiting,
    val roadStates: List<RoadState> = RoadStates,
    var playZoneSize: AreaSize = AreaSize(),
    val score: Int = 0,
    val bestScore: Int = 0,
    var targetRoadIndex: Int = -1,
    val pipeStateList: List<PipeState> = PipeStateList,
    var targetPipeIndex: Int = -1,
    val birdState: BirdState = BirdState()
) {
    val isLifting get() = gameStatus == GameStatus.Running && birdState.isLifting
    val isFalling get() = gameStatus == GameStatus.Running && !birdState.isLifting
    val isQuickFalling get() = gameStatus == GameStatus.Dying
    val isOver get() = gameStatus == GameStatus.Over

    fun reset(bestScore: Int): GameState = GameState(bestScore = bestScore)
}

sealed class GameEvent {
    data object RoadExit : GameEvent()
    data object UpdateScreenSize : GameEvent()
    data object AutoTick : GameEvent()
    data object Start: GameEvent()
    data object TouchLift: GameEvent()
    data object PipeExit: GameEvent()
    data object HitPipe: GameEvent()
    data object HitGround: GameEvent()
    data object CrossedPipe: GameEvent()
    data object Restart: GameEvent()
}

enum class GameStatus {
    Waiting, // wait to start
    Running,
    Dying, // hit pipe and dying
    Over
    // Paused
}
