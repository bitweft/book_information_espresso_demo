package com.bookinformation.api_client


import com.bookinformation.helpers.HttpHelper
import okhttp3.Callback
import okhttp3.HttpUrl

class BookApiClient {
    private val host = "openlibrary.org"

    fun searchBooks(bookName: String, callback: Callback) {
        val url = getSearchBooksUrl(bookName)
        HttpHelper().getResponse(url, callback)
    }

    private fun getSearchBooksUrl(bookName: String): HttpUrl {
        val searchListPath = "search.json"

        return HttpUrl.Builder()
            .scheme("http")
            .host(host)
            .addPathSegment(searchListPath)
            .addEncodedQueryParameter("title", bookName)
            .addEncodedQueryParameter("limit", "5")
            .build()
    }
}
