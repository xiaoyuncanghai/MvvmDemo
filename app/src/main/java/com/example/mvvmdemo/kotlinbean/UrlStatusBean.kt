package com.example.mvvmdemo.kotlinbean

class UrlStatusBean<T> {
    var mData : T? = null
    var mErrorMsg : String? = null

    constructor()

    constructor(data: T?, errorMsg: String?) {
        this.mData = data
        this.mErrorMsg = errorMsg
    }
}