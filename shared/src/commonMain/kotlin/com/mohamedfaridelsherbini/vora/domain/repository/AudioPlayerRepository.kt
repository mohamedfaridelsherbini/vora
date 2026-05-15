package com.mohamedfaridelsherbini.vora.domain.repository

import com.mohamedfaridelsherbini.vora.audio.model.PlaybackState
import kotlinx.coroutines.flow.StateFlow

interface AudioPlayerRepository {
    val playbackState: StateFlow<PlaybackState>

    suspend fun play(audioPath: String)

    suspend fun pause()

    suspend fun stop()
}
