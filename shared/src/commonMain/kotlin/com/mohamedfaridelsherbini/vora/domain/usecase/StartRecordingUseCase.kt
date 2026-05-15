package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.audio.model.RecordingSession
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository

class StartRecordingUseCase(
    private val audioRecorderRepository: AudioRecorderRepository,
) {
    suspend operator fun invoke(
        memoId: String,
        outputPath: String,
        startedAtEpochMs: Long,
    ): RecordingSession = audioRecorderRepository.startRecording(
        memoId = memoId,
        outputPath = outputPath,
        startedAtEpochMs = startedAtEpochMs,
    )
}
