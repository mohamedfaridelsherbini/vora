package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.audio.player.BootstrapAudioPlayerRepository
import com.mohamedfaridelsherbini.vora.audio.recorder.BootstrapAudioRecorderRepository
import com.mohamedfaridelsherbini.vora.data.repository.InMemoryVoiceMemoRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import org.koin.dsl.module

private class DefaultSharedFoundationDependencies(
    override val voiceMemoRepository: VoiceMemoRepository,
    override val audioRecorderRepository: AudioRecorderRepository,
    override val audioPlayerRepository: AudioPlayerRepository,
) : SharedFoundationDependencies

val sharedFoundationModule = module {
    single<VoiceMemoRepository> { InMemoryVoiceMemoRepository() }
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
}
