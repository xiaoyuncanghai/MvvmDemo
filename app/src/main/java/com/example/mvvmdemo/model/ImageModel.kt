package com.example.mvvmdemo.model

import com.example.mvvmdemo.http.GetImageUrlCallback
import com.example.mvvmdemo.http.HttpUtils
import com.example.mvvmdemo.kotlinbean.ImageJsonBean
import com.example.mvvmdemo.kotlinbean.UrlStatusBean
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * model的作用,获取网络数据
 */
class ImageModel {
    val mHttpUtils:HttpUtils
    init {
        mHttpUtils = HttpUtils.mInstant
    }

    fun getUrl(format: String, idx: Int, n: Int, callback: GetImageUrlCallback) {
        mHttpUtils.getImageUrl(format, idx, n).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ImageJsonBean>{
                override fun onComplete() {
                    //do nothing
                }

                override fun onSubscribe(d: Disposable) {
                    //do nothing
                }

                override fun onNext(t: ImageJsonBean) {
                    val data: UrlStatusBean<ImageJsonBean.Image> =
                        UrlStatusBean<ImageJsonBean.Image>(t.images?.get(0), null)
                    callback.handImageUrl(data)
                }

                override fun onError(e: Throwable) {
                    val data: UrlStatusBean<ImageJsonBean.Image> =
                        UrlStatusBean<ImageJsonBean.Image>(null, e.message!!)
                    callback.handImageUrl(data)
                }

            })
    }
}