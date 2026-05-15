package com.mohamedfaridelsherbini.vora.di

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

data class VoiceMemoFeatureUseCases(
    val observeVoiceMemos: ObserveVoiceMemosUseCase,
    val getVoiceMemo: GetVoiceMemoUseCase,
    val upsertVoiceMemo: UpsertVoiceMemoUseCase,
    val renameVoiceMemo: RenameVoiceMemoUseCase,
    val deleteVoiceMemo: DeleteVoiceMemoUseCase,
    val startRecording: StartRecordingUseCase,
    val stopRecording: StopRecordingUseCase,
    val cancelRecording: CancelRecordingUseCase,
    val playVoiceMemo: PlayVoiceMemoUseCase,
    val pausePlayback: PausePlaybackUseCase,
    val stopPlayback: StopPlaybackUseCase,
)
