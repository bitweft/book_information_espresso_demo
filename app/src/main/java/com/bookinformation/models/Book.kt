package com.bookinformation.models

import android.text.TextUtils
import org.json.JSONObject


data class Book(val title: String, val coverUrl: String, val author: String, val bookId: String) {

    companion object {
        private const val coverEditionKey = "cover_edition_key"
        private const val editionKey = "edition_key"
        private var bookId = "OL22620224M"

        fun getBook(book: JSONObject): Book {
            val title = book.getString("title")
            val author = getAuthor(book)
            bookId = getBookId(book)
            val coverUrl = getCoverUrl(bookId)
            return Book(title, coverUrl, author, bookId)
        }

        private fun getBookId(book: JSONObject): String {
            if (book.has(coverEditionKey)) {
                return book.getString(coverEditionKey)
            } else if (book.has(editionKey)) {
                val ids = book.getJSONArray(editionKey)
                return ids.getString(0)
            }
            return bookId
        }

        private fun getAuthor(book: JSONObject): String {
            val authorNames: MutableList<String> = ArrayList()

            val authors = book.getJSONArray("author_name")
            for (i in 0 until authors.length()) {
                authorNames.add(authors.getString(i))
            }
            return TextUtils.join(", ", authorNames)
        }

        private fun getCoverUrl(coverId: String): String {
            return "http://covers.openlibrary.org/b/olid/$coverId-M.jpg?default=false"
        }
    }
}