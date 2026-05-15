package com.mohamedfaridelsherbini.vora.audio.model

sealed interface PlaybackState {
    data object Idle : PlaybackState

    data class Playing(
        val audioPath: String,
        val positionMs: Long,
    ) : PlaybackState

    data class Paused(
        val audioPath: String,
        val positionMs: Long,
    ) : PlaybackState
}
