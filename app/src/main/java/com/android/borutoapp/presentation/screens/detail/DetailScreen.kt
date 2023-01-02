package com.android.borutoapp.presentation.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.borutoapp.utils.Constants.BASE_URL
import com.android.borutoapp.utils.PaletteGenerator.convertImageUrlToBitmap
import com.android.borutoapp.utils.PaletteGenerator.extractColorFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
   navController: NavController,
   viewModel: DetailViewModel = hiltViewModel()
) {
   val selectedHero by viewModel.selectedHero.collectAsState()
   val colorPalette by viewModel.colorPalette

   if (colorPalette.isNotEmpty()) {
      DetailContent(
         navController = navController,
         selectedHero = selectedHero,
         colors = colorPalette
      )
   } else {
      viewModel.generatePaletteColor()
   }

   val context = LocalContext.current

   LaunchedEffect(key1 = true) {
      viewModel.uiEvent.collectLatest { event ->
         when (event) {
            is UiEvent.GeneratePaletteColor -> {
               val bitmap = convertImageUrlToBitmap(
                  imageUrl = "$BASE_URL${selectedHero?.image}",
                  context = context
               )
               if (bitmap != null) {
                  val paletteColors = extractColorFromBitmap(
                     bitmap = bitmap
                  )
                  viewModel.setPaletteColors(paletteColors)
               }
            }
         }
      }
   }
}