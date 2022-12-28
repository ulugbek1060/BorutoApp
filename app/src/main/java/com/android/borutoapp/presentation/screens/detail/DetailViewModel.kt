package com.android.borutoapp.presentation.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ARGUMENT_HERO_ID = "heroId"

@HiltViewModel
class DetailViewModel @Inject constructor(
   private val useCases: UseCases, savedStateHandle: SavedStateHandle
) : ViewModel() {

   private val _selectedHero: MutableStateFlow<Hero?> = MutableStateFlow(null)
   val selectedHero: StateFlow<Hero?> = _selectedHero

   init {
      viewModelScope.launch(Dispatchers.IO) {
         val heroId = savedStateHandle.get<Int>(ARGUMENT_HERO_ID)
         _selectedHero.value = heroId?.let { useCases.selectedHeroUseCase(heroId = heroId) }
      }
   }
}