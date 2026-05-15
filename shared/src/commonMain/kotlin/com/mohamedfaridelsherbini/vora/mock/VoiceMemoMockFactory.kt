package com.mohamedfaridelsherbini.vora.mock

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource

class VoiceMemoMockFactory {
    fun seedMemos(now: Long): List<VoiceMemo> = listOf(
        VoiceMemo(
            id = "memo-morning-idea",
            title = "Morning idea",
            audioPath = "/local/memos/morning-idea.m4a",
            durationMs = 84_000,
            createdAt = now - (55 * 60 * 1000),
            source = VoiceMemoSource.Phone,
        ),
        VoiceMemo(
            id = "memo-pickup-notes",
            title = "Pickup notes",
            audioPath = "/local/memos/pickup-notes.m4a",
            durationMs = 48_000,
            createdAt = now - (6 * 60 * 60 * 1000),
            source = VoiceMemoSource.Watch,
        ),
        VoiceMemo(
            id = "memo-project-review",
            title = "Project review",
            audioPath = "/local/memos/project-review.m4a",
            durationMs = 168_000,
            createdAt = now - (27 * 60 * 60 * 1000),
            source = VoiceMemoSource.Car,
        ),
        VoiceMemo(
            id = "memo-grocery-list",
            title = "Grocery list",
            audioPath = "/local/memos/grocery-list.m4a",
            durationMs = 22_000,
            createdAt = now - (3 * 24 * 60 * 60 * 1000),
            source = VoiceMemoSource.Phone,
        ),
    )

    fun quickMemo(
        index: Int,
        createdAt: Long,
        source: VoiceMemoSource = VoiceMemoSource.Phone,
        idSuffix: String,
    ): VoiceMemo = VoiceMemo(
        id = "memo-$idSuffix",
        title = "Quick memo $index",
        audioPath = "/local/memos/quick-memo-$index.m4a",
        durationMs = 15_000L + (index * 3_000L),
        createdAt = createdAt,
        source = source,
    )
}
