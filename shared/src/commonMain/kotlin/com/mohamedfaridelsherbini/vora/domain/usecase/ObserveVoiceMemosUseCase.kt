package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository
import kotlinx.coroutines.flow.Flow

class ObserveVoiceMemosUseCase(
    private val voiceMemoRepository: VoiceMemoRepository,
) {
    operator fun invoke(): Flow<List<VoiceMemo>> = voiceMemoRepository.observeVoiceMemos()
}
