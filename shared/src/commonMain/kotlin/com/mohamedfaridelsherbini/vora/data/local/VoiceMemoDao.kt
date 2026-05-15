package com.mohamedfaridelsherbini.vora.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceMemoDao {
    @Query("SELECT * FROM VoiceMemoEntity ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<VoiceMemoEntity>>

    @Query("SELECT * FROM VoiceMemoEntity WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): VoiceMemoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(voiceMemo: VoiceMemoEntity)

    @Query("UPDATE VoiceMemoEntity SET title = :title WHERE id = :id")
    suspend fun rename(id: String, title: String)

    @Query("DELETE FROM VoiceMemoEntity WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT COUNT(*) FROM VoiceMemoEntity")
    suspend fun countAll(): Int
}
