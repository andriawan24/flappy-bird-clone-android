package id.andriawan24.flappybirdclone.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.andriawan24.flappybirdclone.ui.components.PipeCapWidth

data class PipeState(
    var offset: Dp = FirstPipeWidthOffset,
    var topHeight: Dp = (LowPipe.value.toInt()..HighPipe.value.toInt()).random().dp,
    var bottomHeight: Dp = TotalPipeHeight - topHeight - PipeDistance,
    var counted: Boolean = false
) {
    fun move(): PipeState = copy(offset = offset - PipeMoveVelocity)
    fun count(): PipeState = copy(counted = true)
    fun correct(): PipeState {
        val newTopHeight = (LowPipe.value.toInt()..HighPipe.value.toInt()).random().dp
        return copy(
            topHeight = newTopHeight,
            bottomHeight = TotalPipeHeight - newTopHeight - PipeDistance
        )
    }

    fun reset(): PipeState {
        val newTopHeight = (LowPipe.value.toInt()..HighPipe.value.toInt()).random().dp
        return copy(
            offset = FirstPipeWidthOffset,
            topHeight = newTopHeight,
            bottomHeight = TotalPipeHeight - newTopHeight - PipeDistance,
            counted = false
        )
    }
}

val TotalPipeWidth = 412.dp

val PreviewPipeWidthOffset = -PipeCapWidth
val FirstPipeWidthOffset = PipeCapWidth * 2
val SecondPipeWidthOffset = (TotalPipeWidth + FirstPipeWidthOffset * 3) / 2

val PipeMoveVelocity = 8.dp
val PipeResetThreshold = 0.dp

val DefaultTotalPipeHeight = 660.dp
var TotalPipeHeight = DefaultTotalPipeHeight

const val MaxPipeFraction = 0.6f
const val MinPipeFraction = 0.2f
const val PipeDistanceFraction = 1.0f - MaxPipeFraction - MinPipeFraction
const val CenterPipeFraction = (MaxPipeFraction + MinPipeFraction) / 2

var HighPipe = TotalPipeHeight * MaxPipeFraction
var MiddlePipe = TotalPipeHeight * CenterPipeFraction
var LowPipe = TotalPipeHeight * MinPipeFraction
var PipeDistance = TotalPipeHeight * PipeDistanceFraction

val PipeStateList = listOf(PipeState(offset = FirstPipeWidthOffset), PipeState(offset = SecondPipeWidthOffset))

enum class PipeStatus {
    BirdComing,
    BirdHit,
    BirdCrossing,
    BirdCrossed
}
