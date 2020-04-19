package com.bookinformation.models

import android.text.TextUtils
import org.json.JSONObject


data class Book(val title: String, val coverUrl: String, val author: String) {

    companion object {
        private const val coverEditionKey = "cover_edition_key"
        private const val editionKey = "edition_key"
        private var coverKey = "OL22620224M"

        fun getBook(book: JSONObject): Book {
            val title = book.getString("title")
            val coverUrl = getCoverUrl(book)
            val author = getAuthor(book)

            return Book(title, coverUrl, author)
        }

        private fun getAuthor(book: JSONObject): String {
            val authorNames: MutableList<String> = ArrayList()

            val authors = book.getJSONArray("author_name")
            for (i in 0 until authors.length()) {
                authorNames.add(authors.getString(i))
            }
            return TextUtils.join(", ", authorNames)
        }

        private fun getCoverUrl(book: JSONObject): String {
            if (book.has(coverEditionKey)) {
                coverKey = book.getString(coverEditionKey)
            } else if (book.has(editionKey)) {
                val ids = book.getJSONArray(editionKey)
                coverKey = ids.getString(0)
            }
            return "http://covers.openlibrary.org/b/olid/$coverKey-M.jpg?default=false"
        }
    }
}