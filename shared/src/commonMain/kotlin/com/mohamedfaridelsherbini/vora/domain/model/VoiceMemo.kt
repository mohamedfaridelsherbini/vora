package com.mohamedfaridelsherbini.vora.domain.model

data class VoiceMemo(
    val id: String,
    val title: String,
    val audioPath: String,
    val durationMs: Long,
    val createdAt: Long,
    val transcript: String? = null,
)
