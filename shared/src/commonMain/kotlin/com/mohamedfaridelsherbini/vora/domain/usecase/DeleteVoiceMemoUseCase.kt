package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository

class DeleteVoiceMemoUseCase(
    private val voiceMemoRepository: VoiceMemoRepository,
) {
    suspend operator fun invoke(id: String) {
        voiceMemoRepository.deleteVoiceMemo(id)
    }
}
