package com.android.borutoapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

   const val VIBRANT = "vibrant"
   const val DARK_VIBRANT = "dark_vibrant"
   const val ON_DARK_VIBRANT = "on_dark_vibrant"

   suspend fun convertImageUrlToBitmap(
      imageUrl: String,
      context: Context
   ): Bitmap? {
      val loader = ImageLoader(context = context)
      val request = ImageRequest.Builder(context = context)
         .data(imageUrl)
         .allowHardware(false)
         .build()
      val imageResult = loader.execute(request)
      return if (imageResult is SuccessResult) {
         (imageResult.drawable as BitmapDrawable).bitmap
      } else {
         null
      }
   }

   fun extractColorFromBitmap(bitmap: Bitmap): Map<String, String> {
      return mapOf(
         VIBRANT to parseSwatchColor(
            color = Palette.from(bitmap).generate().vibrantSwatch
         ),
         DARK_VIBRANT to parseSwatchColor(
            color = Palette.from(bitmap).generate().darkVibrantSwatch
         ),
         ON_DARK_VIBRANT to parseBodyColor(
            color = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
         )
      )
   }

   private fun parseSwatchColor(color: Palette.Swatch?): String {
      return if (color != null) {
         val parseColor = Integer.toHexString(color.rgb)
         return "#$parseColor"
      } else {
         "#000000"
      }
   }

   private fun parseBodyColor(color: Int?): String {
      return if (color != null) {
         val parseColor = Integer.toHexString(color)
         "#$parseColor"
      } else {
         "#FFFFFF"
      }
   }
}