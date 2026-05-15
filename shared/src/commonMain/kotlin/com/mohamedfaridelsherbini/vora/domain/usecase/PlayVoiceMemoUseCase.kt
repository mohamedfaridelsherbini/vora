package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository

class PlayVoiceMemoUseCase(
    private val audioPlayerRepository: AudioPlayerRepository,
) {
    suspend operator fun invoke(audioPath: String) {
        audioPlayerRepository.play(audioPath)
    }
}
