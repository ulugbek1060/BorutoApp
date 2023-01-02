package com.android.borutoapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.borutoapp.navigation.Screen
import com.android.borutoapp.presentation.common.ListContent
import com.android.borutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
   navController: NavController,
   homeViewModel: HomeViewModel = hiltViewModel()
) {

   val systemUiController = rememberSystemUiController()
   systemUiController.setStatusBarColor(
      color = MaterialTheme.colors.statusBarColor
   )


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