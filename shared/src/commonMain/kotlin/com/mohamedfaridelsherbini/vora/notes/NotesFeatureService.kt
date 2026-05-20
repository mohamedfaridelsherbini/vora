package com.mohamedfaridelsherbini.vora.notes

import com.mohamedfaridelsherbini.vora.di.VoiceMemoFeatureUseCases
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource
import com.mohamedfaridelsherbini.vora.mock.VoiceMemoMockFactory
import com.mohamedfaridelsherbini.vora.util.Clock
import com.mohamedfaridelsherbini.vora.util.IdGenerator
import kotlinx.coroutines.flow.first

class NotesFeatureService(
    private val voiceMemoUseCases: VoiceMemoFeatureUseCases,
    private val snapshotFactory: NotesSnapshotFactory,
    private val voiceMemoMockFactory: VoiceMemoMockFactory,
    private val clock: Clock,
    private val idGenerator: IdGenerator,
    private val isDemoMode: Boolean,
) {
    private var selectedFilter = NotesSourceFilter.All
    private var searchQuery = ""

    suspend fun loadSnapshot(): NotesSnapshot =
        snapshotFactory.create(
            memos = voiceMemoUseCases.observeVoiceMemos().first(),
            selectedFilter = selectedFilter,
            searchQuery = searchQuery,
        )

    suspend fun insertMemo() {
        if (isDemoMode) {
            val current = voiceMemoUseCases.observeVoiceMemos().first()
            val nextIndex = (current.size + 1).coerceAtLeast(1)
            val now = clock.currentTimeMillis()
            voiceMemoUseCases.upsertVoiceMemo(
                voiceMemoMockFactory.quickMemo(
                    index = nextIndex,
                    createdAt = now,
                    source = VoiceMemoSource.Phone,
                    idSuffix = idGenerator.randomId(),
                ),
            )
        }
    }

    suspend fun renameMemo(id: String, newTitle: String) {
        val trimmed = newTitle.trim()
        if (trimmed.isBlank()) return
        voiceMemoUseCases.renameVoiceMemo(
            id = id,
            title = trimmed,
        )
    }

    suspend fun deleteMemo(id: String) {
        voiceMemoUseCases.deleteVoiceMemo(id)
    }

    fun selectSourceFilter(key: String) {
        selectedFilter = NotesSourceFilter.fromKey(key)
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
}

data class NotesSnapshot(
    val mode: NotesSnapshotMode,
    val summaryText: String,
    val statusLabel: String,
    val selectedFilterKey: String,
    val searchQuery: String,
    val filters: List<NotesSourceFilterChip>,
    val memos: List<NotesMemoItem>,
)

enum class NotesSnapshotMode {
    Loading,
    Empty,
    Loaded,
}

data class NotesMemoItem(
    val id: String,
    val title: String,
    val time: String,
    val subtitle: String,
    val source: String,
)

data class NotesSourceFilterChip(
    val key: String,
    val label: String,
    val count: Int,
    val selected: Boolean,
)

enum class NotesSourceFilter(
    val key: String,
    val label: String,
) {
    All(key = "all", label = "All"),
    Phone(key = "phone", label = "Phone"),
    Watch(key = "watch", label = "Watch"),
    Car(key = "car", label = "Car");

    companion object {
        fun fromKey(key: String): NotesSourceFilter = entries.firstOrNull { it.key == key } ?: All
    }
}
