package com.example.mvvmdemo.http

import com.example.mvvmdemo.kotlinbean.ImageJsonBean
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpUtils constructor() {
    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder().baseUrl(ImageJsonBean.Image.BASE_IMAGE_ADDRESS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getImageUrl(format: String, idx: Int, n: Int): Observable<ImageJsonBean> {
        return mRetrofit.create(IRequestImage::class.java).request(format, idx, n)
    }

    private object Holder {
        internal val instance = HttpUtils()
    }

    //声明静态变量
    companion object {
        val mInstant: HttpUtils =
            Holder.instance
    }
}