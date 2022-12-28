package com.android.borutoapp.data.repository

import androidx.paging.PagingData
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.repository.DataStoreOperations
import com.android.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
   private val localDataSource: LocalDataSourceImp,
   private val dataStoreOperations: DataStoreOperations,
   private val remoteDataSource: RemoteDataSource
) {

   fun searchHeroes(query: String): Flow<PagingData<Hero>> {
      return remoteDataSource.searchHeroes(query = query)
   }

   fun getAllHeroes(): Flow<PagingData<Hero>> {
      return remoteDataSource.getAllData()
   }

   suspend fun sageOnBoardingState(completed: Boolean) {
      dataStoreOperations.saveOnBoardingState(completed = completed)
   }

   fun readOnBoardingState(): Flow<Boolean> {
      return dataStoreOperations.readOnBoardingState()
   }

   suspend fun getSelectedHero(heroId: Int): Hero {
      return localDataSource.getSelectedHero(heroId = heroId)
   }

}