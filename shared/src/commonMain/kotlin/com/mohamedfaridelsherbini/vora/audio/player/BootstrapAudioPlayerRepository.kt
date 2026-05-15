package com.mohamedfaridelsherbini.vora.audio.player

import com.mohamedfaridelsherbini.vora.audio.model.PlaybackState
import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BootstrapAudioPlayerRepository : AudioPlayerRepository {
    private val mutablePlaybackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)

    override val playbackState: StateFlow<PlaybackState> = mutablePlaybackState.asStateFlow()

    override suspend fun play(audioPath: String) {
        mutablePlaybackState.value = PlaybackState.Playing(
            audioPath = audioPath,
            positionMs = 0L,
        )
    }

    override suspend fun pause() {
        val currentState = mutablePlaybackState.value
        if (currentState is PlaybackState.Playing) {
            mutablePlaybackState.value = PlaybackState.Paused(
                audioPath = currentState.audioPath,
                positionMs = currentState.positionMs,
            )
        }
    }

    override suspend fun stop() {
        mutablePlaybackState.value = PlaybackState.Idle
    }
}
