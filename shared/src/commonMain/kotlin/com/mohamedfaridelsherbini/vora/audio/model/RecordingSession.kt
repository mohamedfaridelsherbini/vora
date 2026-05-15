package com.mohamedfaridelsherbini.vora.audio.model

data class RecordingSession(
    val memoId: String,
    val outputPath: String,
    val startedAtEpochMs: Long,
)
