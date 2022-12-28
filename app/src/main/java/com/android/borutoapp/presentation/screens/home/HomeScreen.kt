package com.android.borutoapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.borutoapp.navigation.Screen
import com.android.borutoapp.presentation.common.ListContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
   navController: NavController,
   homeViewModel: HomeViewModel = hiltViewModel()
) {
   Scaffold(
      topBar = {
         HomeTopBar() {
            navController.navigate(Screen.Search.route)
         }
      },
      content = {
         ListContent(
            heroes = homeViewModel.heroes.collectAsLazyPagingItems(),
            navController = navController
         )
      }
   )
}