package com.example.mvvmdemo.http

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable
import com.example.mvvmdemo.kotlinbean.ImageJsonBean

/**
 * 请求网络接口
 * https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1
 */
interface IRequestImage {

    @GET("HPImageArchive.aspx")
    fun request(@Query("format") format: String,
                @Query("idx") idx:Int,
                @Query("n") n: Int): Observable<ImageJsonBean>
}