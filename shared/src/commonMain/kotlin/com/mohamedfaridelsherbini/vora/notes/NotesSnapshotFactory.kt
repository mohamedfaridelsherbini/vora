package com.mohamedfaridelsherbini.vora.notes

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource
import com.mohamedfaridelsherbini.vora.util.Clock

class NotesSnapshotFactory(private val clock: Clock) {
    fun create(
        memos: List<VoiceMemo>,
        selectedFilter: NotesSourceFilter,
        searchQuery: String,
    ): NotesSnapshot {
        val now = clock.currentTimeMillis()
        val filters = selectedFilter.toFilterChips(memos)
        val filteredMemos = memos.filterBy(selectedFilter).filterBySearch(searchQuery, now)

        if (filteredMemos.isEmpty()) {
            return NotesSnapshot(
                mode = NotesSnapshotMode.Empty,
                summaryText = "0 memos",
                statusLabel = "Synced",
                status = NotesSyncStatus.Synced,
                selectedFilterKey = selectedFilter.key,
                searchQuery = searchQuery,
                filters = filters,
                memos = emptyList(),
            )
        }

        return NotesSnapshot(
            mode = NotesSnapshotMode.Loaded,
            summaryText = "${filteredMemos.size} memos · ${filteredMemos.sumOf { it.durationMs }.toMinutesLabel()}",
            statusLabel = "Synced",
            status = NotesSyncStatus.Synced,
            selectedFilterKey = selectedFilter.key,
            searchQuery = searchQuery,
            filters = filters,
            memos = filteredMemos.map { it.toNotesMemoItem(now) },
        )
    }
}

private fun List<VoiceMemo>.filterBySearch(query: String, now: Long): List<VoiceMemo> {
    val trimmed = query.trim()
    if (trimmed.isEmpty()) return this
    return filter { memo ->
        memo.title.contains(trimmed, ignoreCase = true) ||
            memo.createdAt.toCreatedAtLabel(now).contains(trimmed, ignoreCase = true)
    }
}

private fun List<VoiceMemo>.filterBy(filter: NotesSourceFilter): List<VoiceMemo> = when (filter) {
    NotesSourceFilter.All -> this
    NotesSourceFilter.Phone -> filter { it.source == VoiceMemoSource.Phone }
    NotesSourceFilter.Watch -> filter { it.source == VoiceMemoSource.Watch }
    NotesSourceFilter.Car -> filter { it.source == VoiceMemoSource.Car }
}

private fun NotesSourceFilter.toFilterChips(memos: List<VoiceMemo>): List<NotesSourceFilterChip> {
    val phoneCount = memos.count { it.source == VoiceMemoSource.Phone }
    val watchCount = memos.count { it.source == VoiceMemoSource.Watch }
    val carCount = memos.count { it.source == VoiceMemoSource.Car }
    return listOf(
        NotesSourceFilterChip(
            key = NotesSourceFilter.All.key,
            label = NotesSourceFilter.All.label,
            count = memos.size,
            selected = this == NotesSourceFilter.All,
        ),
        NotesSourceFilterChip(
            key = NotesSourceFilter.Phone.key,
            label = NotesSourceFilter.Phone.label,
            count = phoneCount,
            selected = this == NotesSourceFilter.Phone,
        ),
        NotesSourceFilterChip(
            key = NotesSourceFilter.Watch.key,
            label = NotesSourceFilter.Watch.label,
            count = watchCount,
            selected = this == NotesSourceFilter.Watch,
        ),
        NotesSourceFilterChip(
            key = NotesSourceFilter.Car.key,
            label = NotesSourceFilter.Car.label,
            count = carCount,
            selected = this == NotesSourceFilter.Car,
        ),
    )
}

private fun VoiceMemo.toNotesMemoItem(now: Long): NotesMemoItem = NotesMemoItem(
    id = id,
    title = title,
    time = durationMs.toDurationLabel(),
    subtitle = createdAt.toCreatedAtLabel(now),
    source = source.displayName,
)
