package com.android.borutoapp.domain.use_cases.save_onboarding

import com.android.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(completed: Boolean) {
        repository.sageOnBoardingState(completed)
    }
}