package com.mohamedfaridelsherbini.vora.notes

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource

class NotesSnapshotFactory {
    fun create(
        memos: List<VoiceMemo>,
        selectedFilter: NotesSourceFilter,
    ): NotesSnapshot {
        val filters = selectedFilter.toFilterChips(memos)
        val filteredMemos = memos.filterBy(selectedFilter)

        if (filteredMemos.isEmpty()) {
            return NotesSnapshot(
                mode = NotesSnapshotMode.Empty,
                summaryText = "0 memos",
                statusLabel = "Synced",
                selectedFilterKey = selectedFilter.key,
                filters = filters,
                memos = emptyList(),
            )
        }

        return NotesSnapshot(
            mode = NotesSnapshotMode.Loaded,
            summaryText = "${filteredMemos.size} memos · ${filteredMemos.sumOf { it.durationMs }.toMinutesLabel()}",
            statusLabel = "Synced",
            selectedFilterKey = selectedFilter.key,
            filters = filters,
            memos = filteredMemos.map(VoiceMemo::toNotesMemoItem),
        )
    }
}

private fun List<VoiceMemo>.filterBy(filter: NotesSourceFilter): List<VoiceMemo> = when (filter) {
    NotesSourceFilter.All -> this
    NotesSourceFilter.Phone -> filter { it.source == VoiceMemoSource.Phone }
    NotesSourceFilter.Smart -> filter { it.source == VoiceMemoSource.Watch }
    NotesSourceFilter.Car -> filter { it.source == VoiceMemoSource.Car }
}

private fun NotesSourceFilter.toFilterChips(memos: List<VoiceMemo>): List<NotesSourceFilterChip> {
    val phoneCount = memos.count { it.source == VoiceMemoSource.Phone }
    val smartCount = memos.count { it.source == VoiceMemoSource.Watch }
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
            key = NotesSourceFilter.Smart.key,
            label = NotesSourceFilter.Smart.label,
            count = smartCount,
            selected = this == NotesSourceFilter.Smart,
        ),
        NotesSourceFilterChip(
            key = NotesSourceFilter.Car.key,
            label = NotesSourceFilter.Car.label,
            count = carCount,
            selected = this == NotesSourceFilter.Car,
        ),
    )
}

private fun VoiceMemo.toNotesMemoItem(): NotesMemoItem = NotesMemoItem(
    id = id,
    title = title,
    time = durationMs.toDurationLabel(),
    subtitle = createdAt.toCreatedAtLabel(),
    source = source.displayName,
)
