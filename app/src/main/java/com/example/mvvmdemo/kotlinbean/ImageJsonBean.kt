package com.example.mvvmdemo.kotlinbean

class ImageJsonBean {
    var images: List<Image>? = null
    class Image {
        var copyright: String? = null
        var url: String? = null
        companion object {
            const val BASE_IMAGE_ADDRESS_URL = "https://cn.bing.com/"
        }
    }
}

