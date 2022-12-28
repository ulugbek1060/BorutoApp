package com.android.borutoapp.di

import android.content.Context
import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.data.repository.DataStoreOperationsImpl
import com.android.borutoapp.data.repository.LocalDataSourceImp
import com.android.borutoapp.data.repository.Repository
import com.android.borutoapp.domain.repository.DataStoreOperations
import com.android.borutoapp.domain.repository.LocalDataSource
import com.android.borutoapp.domain.use_cases.UseCases
import com.android.borutoapp.domain.use_cases.heroes_usecase.HeroesUseCase
import com.android.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.android.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.android.borutoapp.domain.use_cases.searchheroes_usecase.SearchHeroesUseCase
import com.android.borutoapp.domain.use_cases.selectedhero_usecase.GetSelectedHeroUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

   @Provides
   @Singleton
   fun provideDateStoreOperations(
      @ApplicationContext context: Context
   ): DataStoreOperations {
      return DataStoreOperationsImpl(context)
   }

   @Provides
   @Singleton
   fun provideUseCases(repository: Repository): UseCases {
      return UseCases(
         saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
         readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
         heroesUseCase = HeroesUseCase(repository),
         searchHeroes = SearchHeroesUseCase(repository),
         selectedHeroUseCase = GetSelectedHeroUseCase(repository)
      )
   }

   @Provides
   @Singleton
   fun provideLocalDataSource(heroDatabase: BorutoDatabase): LocalDataSource {
      return LocalDataSourceImp(heroDatabase)
   }
}