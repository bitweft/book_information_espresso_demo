package com.bookinformation.models

import android.text.TextUtils
import org.json.JSONObject


data class Book(val title: String, val coverUrl: String, val author: String, val bookId: String) {

    companion object {
        private const val coverEditionKey = "cover_edition_key"
        private const val editionKey = "edition_key"

        fun getBook(book: JSONObject): Book {
            val bookId = getBookId(book)
            val title = book.getString("title")
            val author = getAuthor(book)
            val coverUrl = getCoverUrl(bookId)
            return Book(title, coverUrl, author, bookId)
        }

        private fun getBookId(book: JSONObject): String {
            if (book.has(coverEditionKey)) {
                return book.getString(coverEditionKey)
            } else {
                val ids = book.getJSONArray(editionKey)
                return ids.getString(0)
            }
        }

        private fun getAuthor(book: JSONObject): String {
            val authorNames: MutableList<String> = ArrayList()
            val authorNameKey = "author_name"
            if (book.has(authorNameKey)) {
                val authors = book.getJSONArray(authorNameKey)
                for (i in 0 until authors.length()) {
                    authorNames.add(authors.getString(i))
                }
            }
            return TextUtils.join(", ", authorNames)
        }

        private fun getCoverUrl(bookId: String): String {
            return "http://covers.openlibrary.org/b/olid/$bookId-M.jpg?default=false"
        }
    }
}