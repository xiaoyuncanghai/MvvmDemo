package com.example.mvvmdemo.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.http.GetImageUrlCallback
import com.example.mvvmdemo.kotlinbean.ImageJsonBean
import com.example.mvvmdemo.kotlinbean.UrlStatusBean
import com.example.mvvmdemo.model.ImageModel
import kotlin.coroutines.coroutineContext

/**
 * ImageViewModel 继承与 ViewModel
 * ViewModel层的职责是用来处理中转逻辑，将真正获取数据的操作交给Model去执行，
 * 然后将获取到的数据更改到自身的LiveData中去。
 * LiveData中的数据改变后，会去通知监听它改变的、当前状态是可见的View们去更改UI。
 */
class ImageViewModel(var context: Context): ViewModel(){

    companion object{
        const val STATE_NO_PRE_IMAGE = 0 // 没有前一张图片的状态
        const val STATE_NORMAL_IMAGE = 1 // 图片正常获取的状态
    }

    /**
     * 放在LiveData中的数据，会被View监听，
     * 当数据改变且View层属于可见状态，则会去通知View层更新数据
     */
    private var mData: MutableLiveData<UrlStatusBean<ImageJsonBean.Image>> = MutableLiveData<UrlStatusBean<ImageJsonBean.Image>>()
    private var mStatus: MutableLiveData<Int> = MutableLiveData<Int>()
    /**
     * 本例没有用到Context，
     * 若需要，则必须传Appalication的Context，达到与View层独立的目的。
     */
    private var mContext: Context = context.applicationContext
    private var mImageModel: ImageModel = ImageModel()
    private var mCallBack : GetImageUrlCallback
    private var index = 0
    init {
        //Context为null时候抛出异常
        mCallBack = object : GetImageUrlCallback{
            override fun handImageUrl(data: UrlStatusBean<ImageJsonBean.Image>) {
                mData.value = data  //将data设置到livedata里面去
            }
        }
    }

    //提供操作mutableLiveData的方法,给view层使用
    fun getImageData(): MutableLiveData<UrlStatusBean<ImageJsonBean.Image>> = mData

    fun getImageStatus(): MutableLiveData<Int> = mStatus

    fun loadImage() = mImageModel.getUrl("js", index, 1, mCallBack)

    fun loadNextImage() {
        index++
        mImageModel.getUrl("js", index, 1, mCallBack)
    }

    fun loadPreImage(){
        while (index == 0) {
            mStatus.value = STATE_NO_PRE_IMAGE
            mStatus.value = STATE_NORMAL_IMAGE
            return
        }
        index --
        mImageModel.getUrl("js", index, 1, mCallBack)
    }

}