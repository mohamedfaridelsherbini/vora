package com.mohamedfaridelsherbini.vora.notes.presentation.viewmodel

import com.mohamedfaridelsherbini.vora.audio.model.CompletedRecording
import com.mohamedfaridelsherbini.vora.audio.model.PlaybackState
import com.mohamedfaridelsherbini.vora.audio.model.RecordingSession
import com.mohamedfaridelsherbini.vora.di.VoiceMemoFeatureUseCases
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemoSource
import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import com.mohamedfaridelsherbini.vora.domain.usecase.CancelRecordingUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.DeleteVoiceMemoUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.GetVoiceMemoUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.ObserveVoiceMemosUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.PausePlaybackUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.PlayVoiceMemoUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.RenameVoiceMemoUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.StartRecordingUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.StopPlaybackUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.StopRecordingUseCase
import com.mohamedfaridelsherbini.vora.domain.usecase.UpsertVoiceMemoUseCase
import com.mohamedfaridelsherbini.vora.mock.VoiceMemoMockFactory
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.NotesSnapshotFactory
import com.mohamedfaridelsherbini.vora.notes.presentation.action.NotesAction
import com.mohamedfaridelsherbini.vora.util.Clock
import com.mohamedfaridelsherbini.vora.util.IdGenerator
import com.mohamedfaridelsherbini.vora.notes.presentation.state.NotesListMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelActionTest {
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun requestRename_setsRenameDialogForTargetMemo() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()
        val target = viewModel.uiState.value.memos.first().id

        viewModel.onAction(NotesAction.RequestRename(target))

        assertNotNull(viewModel.uiState.value.renameDialog)
        assertEquals(target, viewModel.uiState.value.renameDialog?.memo?.id)
    }

    @Test
    fun requestRename_invalidId_doesNotSetRenameDialog() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.onAction(NotesAction.RequestRename("invalid-id"))

        assertNull(viewModel.uiState.value.renameDialog)
    }

    @Test
    fun dismissRename_clearsRenameDialog() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()
        val target = viewModel.uiState.value.memos.first().id
        viewModel.onAction(NotesAction.RequestRename(target))

        viewModel.onAction(NotesAction.DismissRename)

        assertNull(viewModel.uiState.value.renameDialog)
    }

    @Test
    fun confirmRename_validTitle_renamesMemoAndClearsDialog() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        // 1. Request rename
        viewModel.onAction(NotesAction.RequestRename("memo-a"))
        assertNotNull(viewModel.uiState.value.renameDialog)

        // 2. Confirm rename
        viewModel.onAction(NotesAction.ConfirmRename("Updated morning idea"))
        advanceUntilIdle()

        // Assertions
        assertNull(viewModel.uiState.value.renameDialog)
        val updatedMemo = repository.getVoiceMemo("memo-a")
        assertNotNull(updatedMemo)
        assertEquals("Updated morning idea", updatedMemo.title)
        assertEquals("Updated morning idea", viewModel.uiState.value.memos.first { it.id == "memo-a" }.title)
    }

    @Test
    fun confirmRename_blankTitle_doesNotRenameMemoAndClearsDialog() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        // 1. Request rename
        viewModel.onAction(NotesAction.RequestRename("memo-a"))
        assertNotNull(viewModel.uiState.value.renameDialog)

        // 2. Confirm rename with a blank title
        viewModel.onAction(NotesAction.ConfirmRename("   "))
        advanceUntilIdle()

        // Assertions
        assertNull(viewModel.uiState.value.renameDialog)
        val updatedMemo = repository.getVoiceMemo("memo-a")
        assertNotNull(updatedMemo)
        assertEquals("Morning idea", updatedMemo.title) // Should remain unchanged
    }

    @Test
    fun confirmRename_noActiveRenameDialog_doesNothing() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        // Confirm rename without requesting rename first
        viewModel.onAction(NotesAction.ConfirmRename("New Title"))
        advanceUntilIdle()

        // Title of all memos should remain unchanged
        assertEquals("Morning idea", repository.getVoiceMemo("memo-a")?.title)
        assertEquals("Pickup notes", repository.getVoiceMemo("memo-b")?.title)
    }

    @Test
    fun requestDelete_validId_setsDeleteDialog() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.onAction(NotesAction.RequestDelete("memo-b"))

        assertNotNull(viewModel.uiState.value.deleteDialog)
        assertEquals("memo-b", viewModel.uiState.value.deleteDialog?.memo?.id)
    }

    @Test
    fun requestDelete_invalidId_doesNotSetDeleteDialog() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.onAction(NotesAction.RequestDelete("invalid-id"))

        assertNull(viewModel.uiState.value.deleteDialog)
    }

    @Test
    fun dismissDelete_clearsDeleteDialog() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        viewModel.onAction(NotesAction.RequestDelete("memo-b"))
        assertNotNull(viewModel.uiState.value.deleteDialog)

        viewModel.onAction(NotesAction.DismissDelete)

        assertNull(viewModel.uiState.value.deleteDialog)
    }

    @Test
    fun confirmDelete_deletesMemoAndClearsDialog() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        // 1. Request delete
        viewModel.onAction(NotesAction.RequestDelete("memo-a"))
        assertNotNull(viewModel.uiState.value.deleteDialog)

        // 2. Confirm delete
        viewModel.onAction(NotesAction.ConfirmDelete)
        advanceUntilIdle()

        // Assertions
        assertNull(viewModel.uiState.value.deleteDialog)
        assertNull(repository.getVoiceMemo("memo-a"))
        assertTrue(viewModel.uiState.value.memos.none { it.id == "memo-a" })
    }

    @Test
    fun confirmDelete_noActiveDeleteDialog_doesNothing() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        viewModel.onAction(NotesAction.ConfirmDelete)
        advanceUntilIdle()

        // Memos count should be unchanged
        assertEquals(2, repository.observeVoiceMemos().first().size)
    }

    @Test
    fun recordMemo_insertsNewMemo() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()
        val before = viewModel.uiState.value.memos.size

        viewModel.onAction(NotesAction.RecordMemo)
        advanceUntilIdle()

        val after = viewModel.uiState.value.memos.size
        assertTrue(after > before)
    }

    @Test
    fun search_filtersMemosByTitle() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        // Query that matches only "Morning idea"
        viewModel.onAction(NotesAction.Search("morning"))
        advanceUntilIdle()

        assertEquals("morning", viewModel.uiState.value.searchQuery)
        assertEquals(1, viewModel.uiState.value.memos.size)
        assertEquals("memo-a", viewModel.uiState.value.memos.first().id)
    }

    @Test
    fun search_emptyQuery_showsAllMemos() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        // Apply a filter query first
        viewModel.onAction(NotesAction.Search("morning"))
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.memos.size)

        // Reset query
        viewModel.onAction(NotesAction.Search(""))
        advanceUntilIdle()

        assertEquals("", viewModel.uiState.value.searchQuery)
        assertEquals(2, viewModel.uiState.value.memos.size)
    }

    @Test
    fun selectSourceFilter_validFilter_updatesFilterSelection() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        // Select watch filter
        viewModel.onAction(NotesAction.SelectSourceFilter("watch"))
        advanceUntilIdle()

        val filterChips = viewModel.uiState.value.filters
        val watchChip = filterChips.first { it.key == "watch" }
        val allChip = filterChips.first { it.key == "all" }

        assertTrue(watchChip.selected)
        assertTrue(!allChip.selected)
        assertEquals(1, viewModel.uiState.value.memos.size)
        assertEquals("memo-b", viewModel.uiState.value.memos.first().id) // source is Watch
    }

    @Test
    fun selectSourceFilter_invalidFilter_defaultsToAll() = runTest {
        val viewModel = buildViewModel()
        advanceUntilIdle()

        // Select an invalid filter
        viewModel.onAction(NotesAction.SelectSourceFilter("invalid-filter-key"))
        advanceUntilIdle()

        val filterChips = viewModel.uiState.value.filters
        val allChip = filterChips.first { it.key == "all" }

        assertTrue(allChip.selected)
        assertEquals(2, viewModel.uiState.value.memos.size)
    }

    @Test
    fun initialState_emptyRepository_setsEmptyMode() = runTest {
        val repository = FakeVoiceMemoRepository(emptyList())
        val viewModel = buildViewModel(repository)
        advanceUntilIdle()

        assertEquals(0, viewModel.uiState.value.memos.size)
        assertEquals(NotesListMode.Empty, viewModel.uiState.value.mode)
        assertEquals("0 memos", viewModel.uiState.value.summaryText)
    }

    private fun buildViewModel(repository: FakeVoiceMemoRepository = FakeVoiceMemoRepository(seedMemos())): NotesViewModel {
        val useCases = createUseCases(repository)
        val testClock = object : Clock {
            override fun currentTimeMillis(): Long = 1_700_000_000_000
        }
        val testIdGenerator = object : IdGenerator {
            private var count = 0
            override fun randomId(): String = "test-id-${count++}"
        }
        val snapshotFactory = NotesSnapshotFactory(testClock)
        val service = NotesFeatureService(
            voiceMemoUseCases = useCases,
            snapshotFactory = snapshotFactory,
            voiceMemoMockFactory = VoiceMemoMockFactory(),
            clock = testClock,
            idGenerator = testIdGenerator,
            isDemoMode = true,
        )
        return NotesViewModel(service)
    }

    private fun createUseCases(repository: VoiceMemoRepository): VoiceMemoFeatureUseCases {
        val recorderRepo = FakeAudioRecorderRepository()
        val playerRepo = FakeAudioPlayerRepository()
        return VoiceMemoFeatureUseCases(
            observeVoiceMemos = ObserveVoiceMemosUseCase(repository),
            getVoiceMemo = GetVoiceMemoUseCase(repository),
            upsertVoiceMemo = UpsertVoiceMemoUseCase(repository),
            renameVoiceMemo = RenameVoiceMemoUseCase(repository),
            deleteVoiceMemo = DeleteVoiceMemoUseCase(repository),
            startRecording = StartRecordingUseCase(recorderRepo),
            stopRecording = StopRecordingUseCase(recorderRepo),
            cancelRecording = CancelRecordingUseCase(recorderRepo),
            playVoiceMemo = PlayVoiceMemoUseCase(playerRepo),
            pausePlayback = PausePlaybackUseCase(playerRepo),
            stopPlayback = StopPlaybackUseCase(playerRepo),
        )
    }

    private fun seedMemos(): List<VoiceMemo> = listOf(
        VoiceMemo(
            id = "memo-a",
            title = "Morning idea",
            audioPath = "/memo-a.m4a",
            durationMs = 42_000,
            createdAt = 1_700_000_000_000,
            source = VoiceMemoSource.Phone,
        ),
        VoiceMemo(
            id = "memo-b",
            title = "Pickup notes",
            audioPath = "/memo-b.m4a",
            durationMs = 33_000,
            createdAt = 1_700_000_100_000,
            source = VoiceMemoSource.Watch,
        ),
    )
}

private class FakeVoiceMemoRepository(initial: List<VoiceMemo>) : VoiceMemoRepository {
    private val memos = MutableStateFlow(initial)

    override fun observeVoiceMemos(): Flow<List<VoiceMemo>> = memos.asStateFlow()

    override suspend fun getVoiceMemo(id: String): VoiceMemo? = memos.value.firstOrNull { it.id == id }

    override suspend fun upsertVoiceMemo(voiceMemo: VoiceMemo) {
        memos.update { current ->
            val idx = current.indexOfFirst { it.id == voiceMemo.id }
            if (idx == -1) current + voiceMemo else current.toMutableList().apply { this[idx] = voiceMemo }
        }
    }

    override suspend fun renameVoiceMemo(id: String, title: String) {
        memos.update { current ->
            current.map { memo -> if (memo.id == id) memo.copy(title = title) else memo }
        }
    }

    override suspend fun deleteVoiceMemo(id: String) {
        memos.update { current -> current.filterNot { it.id == id } }
    }
}

private class FakeAudioRecorderRepository : AudioRecorderRepository {
    private val mutableActiveSession = MutableStateFlow<RecordingSession?>(null)
    override val activeSession: StateFlow<RecordingSession?> = mutableActiveSession.asStateFlow()

    override suspend fun startRecording(
        memoId: String,
        outputPath: String,
        startedAtEpochMs: Long,
    ): RecordingSession {
        val session = RecordingSession(memoId, outputPath, startedAtEpochMs)
        mutableActiveSession.value = session
        return session
    }

    override suspend fun stopRecording(): CompletedRecording {
        val session = mutableActiveSession.value ?: RecordingSession("unknown", "/unknown.m4a", 0L)
        mutableActiveSession.value = null
        return CompletedRecording(
            outputPath = session.outputPath,
            durationMs = 0L,
            createdAtEpochMs = session.startedAtEpochMs,
        )
    }

    override suspend fun cancelRecording() {
        mutableActiveSession.value = null
    }
}

private class FakeAudioPlayerRepository : AudioPlayerRepository {
    private val mutablePlaybackState = MutableStateFlow<PlaybackState>(PlaybackState.Idle)
    override val playbackState: StateFlow<PlaybackState> = mutablePlaybackState.asStateFlow()

    override suspend fun play(audioPath: String) {
        mutablePlaybackState.value = PlaybackState.Playing(audioPath, 0L)
    }

    override suspend fun pause() {
        val current = mutablePlaybackState.value
        if (current is PlaybackState.Playing) {
            mutablePlaybackState.value = PlaybackState.Paused(current.audioPath, current.positionMs)
        }
    }

    override suspend fun stop() {
        mutablePlaybackState.value = PlaybackState.Idle
    }
}
