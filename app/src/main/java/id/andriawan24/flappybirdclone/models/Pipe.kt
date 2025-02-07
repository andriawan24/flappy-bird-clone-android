package id.andriawan24.flappybirdclone.models

import androidx.compose.ui.unit.dp

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
