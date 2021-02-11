package com.example.newcomponentstructure.utils

import android.content.Context
import android.transition.CircularPropagation
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newcomponentstructure.R

fun getProgressCircle(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUri")
fun loadImage(view: ImageView,uri: String?){
    view.loadImage(uri, getProgressCircle(view.context))
}