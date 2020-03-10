package com.example.mvvmdemo

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.kotlinbean.ImageJsonBean
import com.example.mvvmdemo.kotlinbean.UrlStatusBean
import com.example.mvvmdemo.viewModel.ImageViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mImageViewMode: ImageViewModel
    lateinit var mDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        /*mImageViewMode = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(ImageViewModel::class.java)*/
        //建立ViewModel，并将Activity的生命周期绑定到ViewModel上
        mImageViewMode = ImageViewModel(this)

        // 为ViewModel的UrlData建立数据监听，并监听数据变化，根据数据更新UI
        mImageViewMode.getImageData().observe(
            this,
            Observer<UrlStatusBean<ImageJsonBean.Image>> { t ->
                mDialog.dismiss()
                if (t?.mErrorMsg != null)
                    Toast.makeText(applicationContext, "111111", Toast.LENGTH_SHORT).show()
                // 监听到数据变化后，通过databinding更改布局UI，若未使用databinding，则需要自己写相关UI更新逻辑
                binding.image = t?.mData
            }
        )
        // 监听特殊状态下更新UI的操作
        mImageViewMode.getImageStatus().observe(this, Observer<Int> { t ->
            mDialog.dismiss();
            if(t == ImageViewModel.STATE_NO_PRE_IMAGE) {
                Toast.makeText(applicationContext, "没有前一张图片了！",
                    Toast.LENGTH_LONG).show()
            }
        })
        binding.clicker = Clicker()
        mDialog = ProgressDialog(this)
        mDialog.setTitle("加载中")
        mDialog.show()
        mImageViewMode.loadImage()
    }

    inner class Clicker() {
        fun onClick(view: View?) {
            mDialog.show()
            when (view?.id) {
                R.id.btn_pre -> mImageViewMode.loadPreImage()
                R.id.btn_next -> mImageViewMode.loadNextImage()
                R.id.btn -> mImageViewMode.loadImage()
            }
        }

    }
}
