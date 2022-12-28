package com.android.borutoapp.presentation.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun DetailScreen(
   navController: NavController,
   viewModel: DetailViewModel = hiltViewModel()
) {
   val selectedHero by viewModel.selectedHero.collectAsState()

   DetailContent(navController = navController, selectedHero = selectedHero)
}