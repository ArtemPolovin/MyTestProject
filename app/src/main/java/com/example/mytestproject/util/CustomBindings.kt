package com.example.mytestproject.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mytestproject.R
import com.squareup.picasso.Picasso


@BindingAdapter("loadIcon")
fun bindingImage(weatherIcon: ImageView, iconID: String?) {
    Picasso.get()
        .load("https://www.weatherbit.io/static/img/icons/$iconID.png")
        .resize(300, 300)
        .error(R.drawable.ic_error)
        .into(weatherIcon)
}