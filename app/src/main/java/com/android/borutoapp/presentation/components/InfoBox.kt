package com.android.borutoapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.borutoapp.R
import com.android.borutoapp.ui.theme.SMALL_PADDING
import com.android.borutoapp.ui.theme.titleColor

@Composable
fun InfoBox(
   icon: Painter,
   iconColor: Color,
   text: String,
   subText: String,
   textColor: Color
) {
   Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
         modifier = Modifier
            .padding(end = SMALL_PADDING)
            .size(32.dp),
         painter = icon,
         contentDescription = null,
         tint = iconColor
      )
      Column {
         Text(
            text = text,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Black
         )
         Text(
            modifier = Modifier.alpha(ContentAlpha.medium),
            text = subText,
            color = textColor,
            fontSize = MaterialTheme.typography.overline.fontSize
         )
      }
   }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun InfoBoxPreView() {
   InfoBox(
      icon = painterResource(id = R.drawable.ic_bolt),
      iconColor = MaterialTheme.colors.primary,
      text = "92",
      subText = "power",
      textColor = MaterialTheme.colors.titleColor
   )
}