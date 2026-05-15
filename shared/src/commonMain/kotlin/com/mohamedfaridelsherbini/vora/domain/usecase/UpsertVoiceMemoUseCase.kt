package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository

class UpsertVoiceMemoUseCase(
    private val voiceMemoRepository: VoiceMemoRepository,
) {
    suspend operator fun invoke(voiceMemo: VoiceMemo) {
        voiceMemoRepository.upsertVoiceMemo(voiceMemo)
    }
}
