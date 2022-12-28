package com.android.borutoapp.presentation.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.borutoapp.presentation.common.ListContent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
   navController: NavHostController,
   searchViewModel: SearchViewModel = hiltViewModel()
) {
   val searchQuery by searchViewModel.searchQuery
   val heroes = searchViewModel.searchHero.collectAsLazyPagingItems()

   Scaffold(
      topBar = {
         SearchTopBar(
            text = searchQuery,
            onTextChange = {
               searchViewModel.updateSearchQuery(query = it)
            },
            onSearchClicked = {
               searchViewModel.searchHeroes(query = it)
            },
            onCloseClicked = {
               navController.popBackStack()
            }
         )
      },
      content = {
         ListContent(heroes = heroes, navController = navController)
      }
   )
}