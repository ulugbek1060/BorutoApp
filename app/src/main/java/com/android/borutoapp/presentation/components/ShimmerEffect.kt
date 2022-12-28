package com.android.borutoapp.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.items
import com.android.borutoapp.ui.theme.*

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ){
        items(count = 2){
            AnimatedShimmerEffect()
        }
    }
}

@Composable
fun AnimatedShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val animate by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = animate)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(HERO_ITEM_HEIGHT),
        color = if (isSystemInDarkTheme())
            Color.Black else ShimmerLightGrey,
        shape = RoundedCornerShape(LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier.padding(all = MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .fillMaxWidth(0.5f)
                    .height(NAME_PLACE_HOLDER_HEIGHT),
                color = if (isSystemInDarkTheme())
                    ShimmerDarkGrey else ShimmerMediumGrey,
                shape = RoundedCornerShape(SMALL_PADDING)
            ) {
            }
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .alpha(alpha = alpha)
                        .fillMaxWidth()
                        .height(ABOUT_PLACE_HOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGrey else ShimmerMediumGrey,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha = alpha)
                            .size(RATING_PLACE_HOLDER_HEIGHT),
                        color = if (isSystemInDarkTheme())
                            ShimmerDarkGrey else ShimmerMediumGrey,
                        shape = RoundedCornerShape(SMALL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShimmerPreView() {
    AnimatedShimmerEffect()
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ShimmerPreViewLight() {
    AnimatedShimmerEffect()
}