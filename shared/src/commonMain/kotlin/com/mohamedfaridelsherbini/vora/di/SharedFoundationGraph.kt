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

class SharedFoundationGraph(
    dependencies: SharedFoundationDependencies,
) {
    val voiceMemo = VoiceMemoFeatureUseCases(
        observeVoiceMemos = ObserveVoiceMemosUseCase(dependencies.voiceMemoRepository),
        getVoiceMemo = GetVoiceMemoUseCase(dependencies.voiceMemoRepository),
        upsertVoiceMemo = UpsertVoiceMemoUseCase(dependencies.voiceMemoRepository),
        renameVoiceMemo = RenameVoiceMemoUseCase(dependencies.voiceMemoRepository),
        deleteVoiceMemo = DeleteVoiceMemoUseCase(dependencies.voiceMemoRepository),
        startRecording = StartRecordingUseCase(dependencies.audioRecorderRepository),
        stopRecording = StopRecordingUseCase(dependencies.audioRecorderRepository),
        cancelRecording = CancelRecordingUseCase(dependencies.audioRecorderRepository),
        playVoiceMemo = PlayVoiceMemoUseCase(dependencies.audioPlayerRepository),
        pausePlayback = PausePlaybackUseCase(dependencies.audioPlayerRepository),
        stopPlayback = StopPlaybackUseCase(dependencies.audioPlayerRepository),
    )
}
