package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.audio.model.CompletedRecording
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository

class StopRecordingUseCase(
    private val audioRecorderRepository: AudioRecorderRepository,
) {
    suspend operator fun invoke(): CompletedRecording = audioRecorderRepository.stopRecording()
}
