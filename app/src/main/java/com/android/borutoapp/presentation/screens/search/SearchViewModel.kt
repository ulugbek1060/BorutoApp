package com.android.borutoapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
   private val useCase: UseCases
) : ViewModel() {

   private val _searchQuery = mutableStateOf("")
   val searchQuery = _searchQuery

   private val _searchHeroes = MutableStateFlow<PagingData<Hero>>(
      PagingData.empty(
         sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true)
         )
      )
   )
   val searchHero = _searchHeroes.asStateFlow()

   fun updateSearchQuery(query: String) {
      _searchQuery.value = query
   }

   fun searchHeroes(query: String) = viewModelScope.launch(Dispatchers.IO) {
      if (query == "") return@launch
      useCase.searchHeroes(query = query)
         .cachedIn(viewModelScope)
         .collectLatest {
            _searchHeroes.value = it
         }
   }
}