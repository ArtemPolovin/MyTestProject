package com.example.mytestproject.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mytestproject.R
import com.squareup.picasso.Picasso


@BindingAdapter("loadImage")
fun bindingImage(image: ImageView, imageUrl: String?) {
    Picasso.get()
        .load(imageUrl)
        .resize(300, 300)
        .error(R.drawable.ic_error)
        .into(image)
}