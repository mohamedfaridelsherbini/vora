package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.audio.player.BootstrapAudioPlayerRepository
import com.mohamedfaridelsherbini.vora.audio.recorder.BootstrapAudioRecorderRepository
import com.mohamedfaridelsherbini.vora.data.repository.RoomVoiceMemoRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import com.mohamedfaridelsherbini.vora.mock.VoiceMemoMockFactory
import com.mohamedfaridelsherbini.vora.notes.NotesFeatureService
import com.mohamedfaridelsherbini.vora.notes.NotesSnapshotFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

private class DefaultSharedFoundationDependencies(
    override val voiceMemoRepository: VoiceMemoRepository,
    override val audioRecorderRepository: AudioRecorderRepository,
    override val audioPlayerRepository: AudioPlayerRepository,
) : SharedFoundationDependencies

val sharedFoundationModule = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    single { VoiceMemoMockFactory() }
    single { NotesSnapshotFactory() }
    single<VoiceMemoRepository> { RoomVoiceMemoRepository(get(), get(), get()) }
    single<AudioRecorderRepository> { BootstrapAudioRecorderRepository() }
    single<AudioPlayerRepository> { BootstrapAudioPlayerRepository() }
    single<SharedFoundationDependencies> {
        DefaultSharedFoundationDependencies(
            voiceMemoRepository = get(),
            audioRecorderRepository = get(),
            audioPlayerRepository = get(),
        )
    }
    single { SharedFoundationGraph(get()) }
    single { get<SharedFoundationGraph>().voiceMemo }
    single { NotesFeatureService(get(), get(), get()) }
}
