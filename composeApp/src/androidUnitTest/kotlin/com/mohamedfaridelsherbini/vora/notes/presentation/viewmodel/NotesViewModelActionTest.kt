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
        advanceTimeBy(500)
        advanceUntilIdle()
        val target = viewModel.uiState.value.memos.first().id

        viewModel.onAction(NotesAction.RequestRename(target))

        assertNotNull(viewModel.uiState.value.renameDialog)
        assertEquals(target, viewModel.uiState.value.renameDialog?.memo?.id)
    }

    @Test
    fun dismissRename_clearsRenameDialog() = runTest {
        val viewModel = buildViewModel()
        advanceTimeBy(500)
        advanceUntilIdle()
        val target = viewModel.uiState.value.memos.first().id
        viewModel.onAction(NotesAction.RequestRename(target))

        viewModel.onAction(NotesAction.DismissRename)

        assertNull(viewModel.uiState.value.renameDialog)
    }

    @Test
    fun recordMemo_insertsNewMemo() = runTest {
        val repository = FakeVoiceMemoRepository(seedMemos())
        val viewModel = buildViewModel(repository)
        advanceTimeBy(500)
        advanceUntilIdle()
        val before = viewModel.uiState.value.memos.size

        viewModel.onAction(NotesAction.RecordMemo)
        advanceUntilIdle()

        val after = viewModel.uiState.value.memos.size
        assertTrue(after > before)
    }

    private fun buildViewModel(repository: FakeVoiceMemoRepository = FakeVoiceMemoRepository(seedMemos())): NotesViewModel {
        val useCases = createUseCases(repository)
        val service = NotesFeatureService(
            voiceMemoUseCases = useCases,
            snapshotFactory = NotesSnapshotFactory(),
            voiceMemoMockFactory = VoiceMemoMockFactory(),
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
