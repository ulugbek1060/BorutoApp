package com.android.borutoapp.domain.use_cases.searchheroes_usecase

import androidx.paging.PagingData
import com.android.borutoapp.data.repository.Repository
import com.android.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
   private val repository: Repository
) {

   operator fun invoke(query: String): Flow<PagingData<Hero>> =
      repository.searchHeroes(query = query)
}