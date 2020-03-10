package com.example.mvvmdemo.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GetBingImageAdapter {
    @BindingAdapter("url")
    @JvmStatic
    fun setImage(iv: ImageView, url: String?) {
        Glide.with(iv)
            .load(url)
            .into(iv)
    }
}