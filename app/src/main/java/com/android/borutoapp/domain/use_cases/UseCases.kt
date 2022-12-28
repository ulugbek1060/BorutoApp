package com.android.borutoapp.domain.use_cases

import com.android.borutoapp.domain.use_cases.heroes_usecase.HeroesUseCase
import com.android.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.android.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.android.borutoapp.domain.use_cases.searchheroes_usecase.SearchHeroesUseCase
import com.android.borutoapp.domain.use_cases.selectedhero_usecase.GetSelectedHeroUseCase

data class UseCases(
   val saveOnBoardingUseCase: SaveOnBoardingUseCase,
   val readOnBoardingUseCase: ReadOnBoardingUseCase,
   val heroesUseCase: HeroesUseCase,
   val searchHeroes: SearchHeroesUseCase,
   val selectedHeroUseCase: GetSelectedHeroUseCase
)
