package com.mohamedfaridelsherbini.vora.audio.model

data class CompletedRecording(
    val outputPath: String,
    val durationMs: Long,
    val createdAtEpochMs: Long,
)
