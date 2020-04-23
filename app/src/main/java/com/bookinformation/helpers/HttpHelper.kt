package com.bookinformation.helpers

import android.util.Log
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpHelper {
    private val client = OkHttpClient()
    private val tag = "HttpHelper"

    fun getResponse(url: HttpUrl, callback: Callback) {
        val request = Request.Builder()
            .url(url)
            .build()

        Log.i(tag, "URL = $url")
        client.newCall(request).enqueue(callback)
    }

}