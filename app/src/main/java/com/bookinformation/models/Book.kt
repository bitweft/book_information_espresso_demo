package com.bookinformation.models

import org.json.JSONObject


data class Book(val title: String, val coverUrl: String) {

    companion object {
        private const val coverEditionKey = "cover_edition_key"
        private const val editionKey = "edition_key"
        private var coverKey = "OL22620224M"

        fun getBook(book: JSONObject): Book {
            val title = book.getString("title")
            val coverUrl = getCoverUrl(book)

            return Book(title, coverUrl)
        }

        private fun getCoverUrl(book: JSONObject): String {
            if (book.has(coverEditionKey)) {
                coverKey = book.getString(coverEditionKey)
            } else if (book.has(editionKey)) {
                val ids = book.getJSONArray(editionKey)
                coverKey = ids.getString(0)
            }
            val coverUrl = "http://covers.openlibrary.org/b/olid/$coverKey-M.jpg?default=false"
            return coverUrl
        }
    }
}