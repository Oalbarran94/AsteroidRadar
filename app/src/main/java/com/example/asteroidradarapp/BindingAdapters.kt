package com.example.asteroidradarapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("pictureOfDayUrl")
fun bindPictureOfDayUrl(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay?.mediaType == "image") {
        Picasso.get().load(pictureOfDay.url).into(imageView)
    }
}