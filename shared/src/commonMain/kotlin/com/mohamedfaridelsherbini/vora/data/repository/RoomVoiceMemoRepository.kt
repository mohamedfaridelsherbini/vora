package com.mohamedfaridelsherbini.vora.data.repository

import com.mohamedfaridelsherbini.vora.data.local.VoiceMemoEntity
import com.mohamedfaridelsherbini.vora.data.local.VoraRoomDatabase
import com.mohamedfaridelsherbini.vora.data.local.toDomain
import com.mohamedfaridelsherbini.vora.data.local.toEntity
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import com.mohamedfaridelsherbini.vora.mock.VoiceMemoMockFactory
import com.mohamedfaridelsherbini.vora.notes.currentTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RoomVoiceMemoRepository(
    database: VoraRoomDatabase,
    private val voiceMemoMockFactory: VoiceMemoMockFactory,
    private val scope: CoroutineScope,
) : VoiceMemoRepository {
    private val dao = database.voiceMemoDao()

    init {
        seedIfEmpty()
    }

    override fun observeVoiceMemos(): Flow<List<VoiceMemo>> =
        dao.observeAll().map { memos -> memos.map(VoiceMemoEntity::toDomain) }

    override suspend fun getVoiceMemo(id: String): VoiceMemo? =
        dao.getById(id)?.toDomain()

    override suspend fun upsertVoiceMemo(voiceMemo: VoiceMemo) {
        dao.upsert(voiceMemo.toEntity())
    }

    override suspend fun renameVoiceMemo(
        id: String,
        title: String,
    ) {
        dao.rename(id = id, title = title)
    }

    override suspend fun deleteVoiceMemo(id: String) {
        dao.delete(id)
    }

    private fun seedIfEmpty() {
        // Keep bootstrap data only until real recording/persistence flows are in place.
        scope.launch {
            if (dao.countAll() > 0) return@launch
            voiceMemoMockFactory.seedMemos(currentTimeMillis()).forEach { voiceMemo ->
                dao.upsert(voiceMemo.toEntity())
            }
        }
    }
}
