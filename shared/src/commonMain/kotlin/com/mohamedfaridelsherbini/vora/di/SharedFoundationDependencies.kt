package com.mohamedfaridelsherbini.vora.di

import com.mohamedfaridelsherbini.vora.domain.repository.AudioPlayerRepository
import com.mohamedfaridelsherbini.vora.domain.repository.AudioRecorderRepository
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository

interface SharedFoundationDependencies {
    val voiceMemoRepository: VoiceMemoRepository
    val audioRecorderRepository: AudioRecorderRepository
    val audioPlayerRepository: AudioPlayerRepository
}
