package com.android.borutoapp.domain.use_cases.selectedhero_usecase

import com.android.borutoapp.data.repository.Repository
import com.android.borutoapp.domain.model.Hero

class GetSelectedHeroUseCase(
   private val repository: Repository
) {
   suspend operator fun invoke(heroId: Int): Hero {
      return repository.getSelectedHero(heroId = heroId)
   }
}