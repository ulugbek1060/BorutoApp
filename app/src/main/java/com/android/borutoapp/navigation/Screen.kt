package com.android.borutoapp.navigation

import com.android.borutoapp.presentation.screens.detail.ARGUMENT_HERO_ID

sealed class Screen(val route: String) {
   object Splash : Screen("splash_screen")
   object Welcome : Screen("welcome_screen")
   object Home : Screen("home_screen")
   object Details : Screen("detail_screen/{${ARGUMENT_HERO_ID}}") {
      fun passHeroId(heroId: Int): String {
         return "detail_screen/$heroId"
      }
   }

   object Search : Screen("search_screen")
}