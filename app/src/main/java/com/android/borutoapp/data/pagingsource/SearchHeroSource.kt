package com.android.borutoapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.borutoapp.data.remote.BorutoApi
import com.android.borutoapp.domain.model.Hero

class SearchHeroSource(
   private val borutoApi: BorutoApi, private val query: String
) : PagingSource<Int, Hero>() {

   override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
      return state.anchorPosition
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
      return try {
         val response = borutoApi.searchHeroes(query = query)
         if (response.heroes.isNotEmpty()) {
            LoadResult.Page(
               data = response.heroes, prevKey = response.prevPage, nextKey = response.nextPage
            )
         } else {
            LoadResult.Page(
               data = emptyList(), prevKey = null, nextKey = null
            )
         }
      } catch (e: Exception) {
         LoadResult.Error(e)
      }
   }

}