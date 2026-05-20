package com.mohamedfaridelsherbini.vora.data.repository

import com.mohamedfaridelsherbini.vora.data.local.VoiceMemoEntity
import com.mohamedfaridelsherbini.vora.data.local.VoraRoomDatabase
import com.mohamedfaridelsherbini.vora.data.local.toDomain
import com.mohamedfaridelsherbini.vora.data.local.toEntity
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomVoiceMemoRepository(
    database: VoraRoomDatabase,
) : VoiceMemoRepository {
    private val dao = database.voiceMemoDao()

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
}
