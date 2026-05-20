package com.mohamedfaridelsherbini.vora.notes

import org.koin.mp.KoinPlatform

class IosNotesBridge(
    private val notesFeatureService: NotesFeatureService = KoinPlatform.getKoin().get(),
) {

    suspend fun loadSnapshot(): NotesSnapshot = notesFeatureService.loadSnapshot()

    suspend fun insertMemo() {
        notesFeatureService.insertMemo()
    }

    suspend fun renameMemo(id: String, newTitle: String) {
        notesFeatureService.renameMemo(id = id, newTitle = newTitle)
    }

    suspend fun deleteMemo(id: String) {
        notesFeatureService.deleteMemo(id)
    }

    fun selectSourceFilter(key: String) {
        notesFeatureService.selectSourceFilter(key)
    }

    fun updateSearchQuery(query: String) {
        notesFeatureService.updateSearchQuery(query)
    }
}
