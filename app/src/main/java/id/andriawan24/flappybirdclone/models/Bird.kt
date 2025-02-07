package id.andriawan24.flappybirdclone.models

import androidx.compose.ui.unit.dp


val DefaultBirdHeightOffset = 0.dp

const val BirdPipeDistanceFraction = 0.30f
var BirdSizeHeight = PipeDistance * BirdPipeDistanceFraction
var BirdSizeWidth = BirdSizeHeight * 1.44f

const val BirdFallToGroundTimes = 20
var BirdFallVelocity = 8.dp
var BirdQuickFallVelocity = BirdFallVelocity * 4

val BirdLiftVelocity = BirdFallVelocity * 8
val BirdQuickLiftVelocity = BirdLiftVelocity * 1.5f
