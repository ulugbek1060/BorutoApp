package com.android.borutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.borutoapp.R
import com.android.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.android.borutoapp.ui.theme.LightGray
import com.android.borutoapp.ui.theme.StarColor


@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val ratingCalculation = calculateStars(rating = rating)

    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        ratingCalculation[FILLED_STARS]?.let {
            repeat(it) {
                FilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        ratingCalculation[HALF_FILLED_STARS]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        ratingCalculation[EMPTY_FILLED_STARS]?.let {
            repeat(it) {
                EmptyStar(
                    starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size

        scale(scaleFactor) {
            val pathWith = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2) - (pathWith / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(
                left = left,
                top = top,
            ) {
                drawPath(
                    path = starPath,
                    color = StarColor,
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size

        scale(scaleFactor) {
            val pathWith = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2) - (pathWith / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(
                left = left,
                top = top,
            ) {

                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f),
                )

                clipPath(path = starPath) {

                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size

        scale(scaleFactor) {
            val pathWith = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width / 2) - (pathWith / 1.7f)
            val top = (canvasSize.height / 2) - (pathHeight / 1.7f)

            translate(
                left = left,
                top = top,
            ) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(0.5f),
                )
            }
        }
    }
}

private const val FILLED_STARS = "filled_stars"
private const val HALF_FILLED_STARS = "half_filled_stars"
private const val EMPTY_FILLED_STARS = "empty_filled_stars"

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating){
        val (firstNumber, lastNumber) = rating
            .toString()
            .split('.')
            .map { it.toInt() }

        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) {
                halfFilledStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d("RatingWidget", "Invalid Rating number.")
        }
    }

    emptyStars = maxStars - (filledStars + halfFilledStars)
    return mapOf(
        FILLED_STARS to filledStars,
        HALF_FILLED_STARS to halfFilledStars,
        EMPTY_FILLED_STARS to emptyStars
    )
}

@Preview(showBackground = true)
@Composable
fun FiledStarPreView() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}

@Preview(showBackground = true)
@Composable
fun HalfFiledStarPreView() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    HalfFilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyFiledStarPreView() {
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 3f
    )
}