package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.model.VoiceMemo
import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository

class GetVoiceMemoUseCase(
    private val voiceMemoRepository: VoiceMemoRepository,
) {
    suspend operator fun invoke(id: String): VoiceMemo? = voiceMemoRepository.getVoiceMemo(id)
}
