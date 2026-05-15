package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository

class PausePlaybackUseCase(
    private val audioPlayerRepository: AudioPlayerRepository,
) {
    suspend operator fun invoke() {
        audioPlayerRepository.pause()
    }
}
