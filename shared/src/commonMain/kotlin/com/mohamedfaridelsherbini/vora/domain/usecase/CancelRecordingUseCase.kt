package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository

class CancelRecordingUseCase(
    private val audioRecorderRepository: AudioRecorderRepository,
) {
    suspend operator fun invoke() {
        audioRecorderRepository.cancelRecording()
    }
}
