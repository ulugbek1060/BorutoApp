package com.android.borutoapp.domain.use_cases.heroes_usecase

import androidx.paging.PagingData
import com.android.borutoapp.data.repository.Repository
import com.android.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HeroesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Hero>> =
        repository.getAllHeroes()
}