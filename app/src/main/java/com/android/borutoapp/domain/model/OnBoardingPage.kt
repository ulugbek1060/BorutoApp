package com.android.borutoapp.domain.model

import androidx.annotation.DrawableRes
import com.android.borutoapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.greetings,
        title = "Greetings",
        description = "Are you a Boruto fun? Because if you are then we have a great new for you!"
    )

    object Second : OnBoardingPage(
        image = R.drawable.explore,
        title = "Explore",
        description = "Find your favorite heroes and learn some of things then you didn't know about."
    )

    object Third : OnBoardingPage(
        image = R.drawable.power,
        title = "Power",
        description = "Check out your hero's power and see how much they are string comparing to others."
    )
}
