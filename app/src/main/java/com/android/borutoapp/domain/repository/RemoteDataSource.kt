package com.android.borutoapp.domain.repository

import androidx.paging.PagingData
import com.android.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
   fun getAllData(): Flow<PagingData<Hero>>
   fun searchHeroes(query: String): Flow<PagingData<Hero>>
}