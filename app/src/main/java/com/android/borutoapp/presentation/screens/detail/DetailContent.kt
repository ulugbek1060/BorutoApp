package com.android.borutoapp.presentation.screens.detail

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.android.borutoapp.R
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.presentation.components.InfoBox
import com.android.borutoapp.presentation.components.OrderList
import com.android.borutoapp.ui.theme.*
import com.android.borutoapp.utils.Constants.BASE_URL
import com.android.borutoapp.utils.PaletteGenerator.DARK_VIBRANT
import com.android.borutoapp.utils.PaletteGenerator.ON_DARK_VIBRANT
import com.android.borutoapp.utils.PaletteGenerator.VIBRANT
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
   navController: NavController,
   selectedHero: Hero?,
   colors: Map<String, String>
) {
   var vibrant by remember { mutableStateOf("#000000") }
   var darkVibrant by remember { mutableStateOf("#000000") }
   var onDarkVibrant by remember { mutableStateOf("#FFFFFF") }

   LaunchedEffect(key1 = selectedHero) {
      vibrant = colors[VIBRANT]!!
      darkVibrant = colors[DARK_VIBRANT]!!
      onDarkVibrant = colors[ON_DARK_VIBRANT]!!
   }

   val systemUiController = rememberSystemUiController()
   systemUiController.setStatusBarColor(
       color = Color(parseColor(darkVibrant))
   )

   val bottomSheetState = rememberBottomSheetScaffoldState(
      bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
   )

   val currentSheetFraction = bottomSheetState.currentSheetFraction

   val radiusAnim by animateDpAsState(
      targetValue = if (currentSheetFraction == 1f) EXTRA_LARGE_PADDING else 0.dp
   )

   BottomSheetScaffold(
      sheetShape = RoundedCornerShape(
         topEnd = radiusAnim,
         topStart = radiusAnim
      ),
      scaffoldState = bottomSheetState,
      sheetPeekHeight = SMALL_BOTTOM_SHEET_HEIGHT,
      sheetContent = {
         selectedHero?.let {
            BottomSheetContent(
               selectedHero = it,
               infoBoxIconColor = Color(parseColor(vibrant)),
               sheetBackgroundColor = Color(parseColor(darkVibrant)),
               contentColor = Color(parseColor(onDarkVibrant))
            )
         }
      },
      content = {
         selectedHero?.let { hero ->
            BackgroundContent(
               heroImage = hero.image,
               imageFraction = currentSheetFraction,
               backgroundColor = Color(parseColor(darkVibrant)),
               onCloseClicked = {
                  navController.popBackStack()
               }
            )
         }
      }
   )
}

private const val TAG = "DetailContent"

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
   get() {
      val fraction = bottomSheetState.progress.fraction
      val targetValue = bottomSheetState.targetValue
      val currentValue = bottomSheetState.currentValue

//      Log.d(TAG, "fraction: ${fraction.toString()}")
//      Log.d(TAG, "targetValue: ${targetValue.toString()}")
//      Log.d(TAG, "currentValue: ${currentValue.toString()}")

      return when {
         currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
         currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
         currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
         currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
         else -> fraction
      }
   }


@Composable
fun BackgroundContent(
   heroImage: String,
   imageFraction: Float = 1f,
   backgroundColor: Color = MaterialTheme.colors.surface,
   onCloseClicked: () -> Unit
) {
   val imageUrl = "$BASE_URL$heroImage"
   Box(
      modifier = Modifier
         .fillMaxSize()
         .background(backgroundColor)
   ) {
      AsyncImage(
         modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = imageFraction + 0.4f)
            .align(Alignment.TopStart),
         model = imageUrl,
         placeholder = painterResource(R.drawable.ic_placeholder),
         error = painterResource(R.drawable.ic_placeholder),
         contentDescription = null,
         contentScale = ContentScale.Crop
      )
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.End
      ) {
         IconButton(
            modifier = Modifier.padding(all = SMALL_PADDING),
            onClick = onCloseClicked
         ) {
            Icon(
               modifier = Modifier.size(32.dp),
               imageVector = Icons.Default.Close,
               contentDescription = null,
               tint = Color.White
            )
         }
      }
   }
}

@Composable
fun BottomSheetContent(
   selectedHero: Hero,
   infoBoxIconColor: Color = MaterialTheme.colors.primary,
   sheetBackgroundColor: Color = MaterialTheme.colors.surface,
   contentColor: Color = MaterialTheme.colors.titleColor,
) {
   Column(
      modifier = Modifier
         .background(sheetBackgroundColor)
         .padding(all = LARGE_PADDING)
   ) {
      Row(
         modifier = Modifier.padding(bottom = LARGE_PADDING),
         verticalAlignment = Alignment.CenterVertically
      ) {
         Icon(
            modifier = Modifier
               .size(32.dp)
               .weight(2f),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            tint = contentColor
         )
         Text(
            modifier = Modifier.weight(8f),
            text = selectedHero.name,
            color = contentColor,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold
         )
      }
      Row(
         modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MEDIUM_PADDING),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         InfoBox(
            icon = painterResource(id = R.drawable.ic_bolt),
            iconColor = infoBoxIconColor,
            text = "${selectedHero.power}",
            subText = stringResource(R.string.paoer),
            textColor = contentColor
         )
         InfoBox(
            icon = painterResource(id = R.drawable.ic_cake),
            iconColor = infoBoxIconColor,
            text = selectedHero.month,
            subText = stringResource(R.string.month),
            textColor = contentColor
         )
         InfoBox(
            icon = painterResource(id = R.drawable.ic_cake),
            iconColor = infoBoxIconColor,
            text = selectedHero.day,
            subText = stringResource(R.string.birthday),
            textColor = contentColor
         )
      }
      Text(
         modifier = Modifier
            .alpha(ContentAlpha.medium)
            .padding(bottom = MEDIUM_PADDING),
         text = "About",
         color = contentColor,
         fontSize = MaterialTheme.typography.subtitle1.fontSize,
         fontWeight = FontWeight.Bold
      )
      Text(
         modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MEDIUM_PADDING),
         text = selectedHero.about,
         color = contentColor,
         fontSize = MaterialTheme.typography.body1.fontSize,
         maxLines = ABOUT_TEXT_MAX_LINES
      )
      Row(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween
      ) {
         OrderList(
            title = "Family",
            items = selectedHero.family,
            textColor = contentColor
         )
         OrderList(
            title = "Ability",
            items = selectedHero.abilities,
            textColor = contentColor
         )
         OrderList(
            title = "Nature Type",
            items = selectedHero.natureTypes,
            textColor = contentColor
         )
      }
   }
}