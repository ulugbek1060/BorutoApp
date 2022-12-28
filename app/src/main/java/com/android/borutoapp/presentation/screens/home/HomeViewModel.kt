package com.android.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   useCases: UseCases
) : ViewModel() {

   val heroes: Flow<PagingData<Hero>> = useCases
      .heroesUseCase()
      .cachedIn(viewModelScope)

}