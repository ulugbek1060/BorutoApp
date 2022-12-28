package com.android.borutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.borutoapp.data.local.BorutoDatabase
import com.android.borutoapp.data.pagingsource.HeroRemoteMediator
import com.android.borutoapp.data.pagingsource.SearchHeroSource
import com.android.borutoapp.data.remote.BorutoApi
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
   private val borutoApi: BorutoApi,
   private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

   private val heroDao = borutoDatabase.heroDao()

   @OptIn(ExperimentalPagingApi::class)
   override fun getAllData(): Flow<PagingData<Hero>> {
      val pagingSourceFactory = { heroDao.getAllHeroes() }
      return Pager(
         config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE
         ),
         remoteMediator = HeroRemoteMediator(
            borutoApi = borutoApi,
            borutoDatabase = borutoDatabase
         ),
         pagingSourceFactory = pagingSourceFactory
      ).flow
   }

   override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
      return Pager(
         config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE
         ),
         pagingSourceFactory = {
            SearchHeroSource(
               borutoApi = borutoApi,
               query = query
            )
         }
      ).flow
   }

   companion object {
      private const val DEFAULT_PAGE_SIZE = 3
   }
}