package com.bookinformation.api_client


import com.bookinformation.constants.Constants
import com.bookinformation.helpers.HttpHelper
import okhttp3.Callback
import okhttp3.HttpUrl

class BookApiClient {
    private val host = Constants.OPEN_LIBRARY_HOST
    private val port = Constants.OPEN_LIBRARY_PORT

    fun searchBooks(bookName: String, callback: Callback) {
        val url = getSearchBooksUrl(bookName)
        HttpHelper().getResponse(url, callback)
    }

    fun getBookDetails(bookId: String, callback: Callback) {
        val url = getBookDetailsUrl(bookId)
        HttpHelper().getResponse(url, callback)
    }

    private fun getSearchBooksUrl(bookName: String): HttpUrl {
        val searchListPath = "search.json"

        return HttpUrl.Builder()
            .scheme("http")
            .host(host)
            .port(port.toInt())
            .addPathSegment(searchListPath)
            .addQueryParameter("title", bookName)
            .addQueryParameter("limit", "15")
            .build()
    }

    private fun getBookDetailsUrl(bookId: String): HttpUrl {
        val bookDetailPath = "books"
        return HttpUrl.Builder()
            .scheme("http")
            .host(host)
            .port(port.toInt())
            .addPathSegment(bookDetailPath)
            .addPathSegment("$bookId.json")
            .build()
    }
}
