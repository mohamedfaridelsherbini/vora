package com.mohamedfaridelsherbini.vora.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource

@Entity
data class VoiceMemoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val audioPath: String,
    val durationMs: Long,
    val createdAt: Long,
    val source: String,
    val transcript: String?,
)

internal fun VoiceMemoEntity.toDomain(): VoiceMemo = VoiceMemo(
    id = id,
    title = title,
    audioPath = audioPath,
    durationMs = durationMs,
    createdAt = createdAt,
    source = VoiceMemoSource.fromStorage(source),
    transcript = transcript,
)

internal fun VoiceMemo.toEntity(): VoiceMemoEntity = VoiceMemoEntity(
    id = id,
    title = title,
    audioPath = audioPath,
    durationMs = durationMs,
    createdAt = createdAt,
    source = source.storageValue,
    transcript = transcript,
)
