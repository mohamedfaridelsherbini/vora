package com.mohamedfaridelsherbini.vora.domain.repository

import com.mohamedfaridelsherbini.vora.audio.model.CompletedRecording
import com.mohamedfaridelsherbini.vora.audio.model.RecordingSession
import kotlinx.coroutines.flow.StateFlow

interface AudioRecorderRepository {
    val activeSession: StateFlow<RecordingSession?>

    suspend fun startRecording(
        memoId: String,
        outputPath: String,
        startedAtEpochMs: Long,
    ): RecordingSession

    suspend fun stopRecording(): CompletedRecording

    suspend fun cancelRecording()
}
