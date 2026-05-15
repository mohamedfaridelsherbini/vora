package com.mohamedfaridelsherbini.vora.domain.repository

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import kotlinx.coroutines.flow.Flow

interface VoiceMemoRepository {
    fun observeVoiceMemos(): Flow<List<VoiceMemo>>

    suspend fun getVoiceMemo(id: String): VoiceMemo?

    suspend fun upsertVoiceMemo(voiceMemo: VoiceMemo)

    suspend fun renameVoiceMemo(
        id: String,
        title: String,
    )

    suspend fun deleteVoiceMemo(id: String)
}
