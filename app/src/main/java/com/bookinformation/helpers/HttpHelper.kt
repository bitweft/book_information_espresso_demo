package com.bookinformation.helpers

import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpHelper {
    private val client = OkHttpClient()

    fun getResponse(url: HttpUrl, callback: Callback) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(callback)
    }

}