package id.andriawan24.flappybirdclone.ui.state

import id.andriawan24.flappybirdclone.models.AreaSize

data class GameState(
    val roadStates: List<RoadState> = RoadStates,
    var playZoneSize: AreaSize = AreaSize(),
    val score: Int = 0,
    val bestScore: Int = 0,
    var targetRoadIndex: Int = -1
)

sealed class GameEvent {
    data object RoadExit : GameEvent()
    data object UpdateScreenSize : GameEvent()
    data object AutoTick : GameEvent()
}
