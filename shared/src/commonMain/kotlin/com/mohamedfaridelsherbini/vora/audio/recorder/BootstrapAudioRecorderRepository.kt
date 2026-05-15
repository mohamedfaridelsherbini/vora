package com.mohamedfaridelsherbini.vora.audio.recorder

import com.mohamedfaridelsherbini.vora.audio.model.CompletedRecording
import com.mohamedfaridelsherbini.vora.audio.model.RecordingSession
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BootstrapAudioRecorderRepository : AudioRecorderRepository {
    private val mutableActiveSession = MutableStateFlow<RecordingSession?>(null)

    override val activeSession: StateFlow<RecordingSession?> = mutableActiveSession.asStateFlow()

    override suspend fun startRecording(
        memoId: String,
        outputPath: String,
        startedAtEpochMs: Long,
    ): RecordingSession {
        check(mutableActiveSession.value == null) { "Recording already active." }

        return RecordingSession(
            memoId = memoId,
            outputPath = outputPath,
            startedAtEpochMs = startedAtEpochMs,
        ).also { session ->
            mutableActiveSession.value = session
        }
    }

    override suspend fun stopRecording(): CompletedRecording {
        val session = requireNotNull(mutableActiveSession.value) { "No active recording session." }
        mutableActiveSession.value = null
        return CompletedRecording(
            outputPath = session.outputPath,
            durationMs = 0L,
            createdAtEpochMs = session.startedAtEpochMs,
        )
    }

    override suspend fun cancelRecording() {
        mutableActiveSession.value = null
    }
}
