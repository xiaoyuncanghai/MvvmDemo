package com.example.mvvmdemo.http

import com.example.mvvmdemo.kotlinbean.ImageJsonBean
import com.example.mvvmdemo.kotlinbean.UrlStatusBean

interface GetImageUrlCallback {
    fun handImageUrl(data : UrlStatusBean<ImageJsonBean.Image>)
}