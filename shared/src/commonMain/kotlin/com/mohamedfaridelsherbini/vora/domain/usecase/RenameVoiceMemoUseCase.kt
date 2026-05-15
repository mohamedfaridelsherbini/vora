package com.mohamedfaridelsherbini.vora.domain.usecase

import com.mohamedfaridelsherbini.vora.domain.repository.VoiceMemoRepository

class RenameVoiceMemoUseCase(
    private val voiceMemoRepository: VoiceMemoRepository,
) {
    suspend operator fun invoke(
        id: String,
        title: String,
    ) {
        voiceMemoRepository.renameVoiceMemo(id, title)
    }
}
