package com.android.borutoapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.android.borutoapp.R
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.ui.theme.DarkGray
import com.android.borutoapp.ui.theme.LightGray
import com.android.borutoapp.ui.theme.NETWORK_ERROR_ICON_SIZE
import com.android.borutoapp.ui.theme.SMALL_PADDING
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

private const val TAG = "EmptyScreen"

@Composable
fun EmptyScreen(
   error: LoadState.Error? = null,
   heroes: LazyPagingItems<Hero>? = null
) {
   var message by remember {
      mutableStateOf("Search your favorite heroes.")
   }

   var icon by remember {
      mutableStateOf(R.drawable.ic_search_document)
   }

   if (error != null) {
      message = parseErrorMessage(error)
      icon = R.drawable.ic_network_error
   }

   var startAnimation by remember {
      mutableStateOf(false)
   }
   val alphaAnim by animateFloatAsState(
      targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
      animationSpec = tween(
         durationMillis = 1000
      )
   )
   LaunchedEffect(true) {
      startAnimation = true
   }
   EmptyContent(
      alphaAnim = alphaAnim,
      icon = icon,
      message = message,
      heroes = heroes,
      error = error
   )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyContent(
   alphaAnim: Float,
   @DrawableRes icon: Int,
   message: String,
   heroes: LazyPagingItems<Hero>?,
   error: LoadState.Error?
) {
   val refreshScope = rememberCoroutineScope()

   var refreshing by remember {
      mutableStateOf(false)
   }

   val state = rememberPullRefreshState(
      refreshing = refreshing,
      onRefresh = {
         refreshScope.launch {
            refreshing = true
            heroes?.refresh()
            refreshing = false
         }
      }
   )

   Box(
      modifier = Modifier
         .pullRefresh(state)
   ) {
      Column(
         modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .alpha(alpha = alphaAnim),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center
      ) {
         Icon(
            modifier = Modifier
               .size(NETWORK_ERROR_ICON_SIZE),
            painter = painterResource(id = icon),
            contentDescription = "Network Error.",
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray
         )
         Text(
            modifier = Modifier
               .padding(top = SMALL_PADDING),
            text = message,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
         )
      }
      PullRefreshIndicator(
         refreshing = refreshing,
         state = state,
         modifier = Modifier.align(Alignment.TopCenter)
      )
   }
}

fun parseErrorMessage(errorState: LoadState.Error): String {
   return when (errorState.error) {
      is SocketTimeoutException -> {
         "Server Unavailable."
      }
      is ConnectException -> {
         "Internet Unavailable."
      }
      else -> {
         "Unknown error."
      }
   }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun EmptyScreenPreViewLight() {
//   EmptyContent(
//      alphaAnim = ContentAlpha.disabled,
//      icon = R.drawable.ic_network_error,
//      message = "Network Unavailable."
//   )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreViewDark() {
//   EmptyContent(
//      alphaAnim = ContentAlpha.disabled,
//      icon = R.drawable.ic_network_error,
//      message = "Network Unavailable."
//   )
}
