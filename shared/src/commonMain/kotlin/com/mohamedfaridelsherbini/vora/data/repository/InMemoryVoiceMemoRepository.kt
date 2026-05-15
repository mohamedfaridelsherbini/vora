package com.mohamedfaridelsherbini.vora.data.repository

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryVoiceMemoRepository : VoiceMemoRepository {
    private val memos = MutableStateFlow<List<VoiceMemo>>(emptyList())

    override fun observeVoiceMemos(): Flow<List<VoiceMemo>> = memos

    override suspend fun getVoiceMemo(id: String): VoiceMemo? = memos.value.firstOrNull { it.id == id }

    override suspend fun upsertVoiceMemo(voiceMemo: VoiceMemo) {
        memos.update { existing ->
            existing
                .filterNot { it.id == voiceMemo.id }
                .plus(voiceMemo)
                .sortedByDescending { it.createdAt }
        }
    }

    override suspend fun renameVoiceMemo(
        id: String,
        title: String,
    ) {
        memos.update { existing ->
            existing.map { memo ->
                if (memo.id == id) {
                    memo.copy(title = title)
                } else {
                    memo
                }
            }
        }
    }

    override suspend fun deleteVoiceMemo(id: String) {
        memos.update { existing -> existing.filterNot { it.id == id } }
    }
}
