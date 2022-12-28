package com.android.borutoapp.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.borutoapp.R
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.presentation.components.InfoBox
import com.android.borutoapp.presentation.components.OrderList
import com.android.borutoapp.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
   navController: NavController,
   selectedHero: Hero?
) {

   val bottomSheetState = rememberBottomSheetScaffoldState(
      bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
   )

   BottomSheetScaffold(
      scaffoldState = bottomSheetState,
      sheetPeekHeight = SMALL_BOTTOM_SHEET_HEIGHT,
      sheetContent = {
         selectedHero?.let { BottomSheetContent(selectedHero = it) }
      },
      content = {}
   )
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