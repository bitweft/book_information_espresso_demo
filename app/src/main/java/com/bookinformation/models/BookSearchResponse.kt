package com.bookinformation.models

import com.bookinformation.models.Book.Companion.getBook
import org.json.JSONObject

data class BookSearchResponse(val books: List<Book>) {

    companion object {
        fun getBookSearchResponse(res: String): BookSearchResponse {
            val books: MutableList<Book> = ArrayList()

            val response = JSONObject(res)
            val docs = response.getJSONArray("docs")
            for (i in 0 until docs.length()) {
                val booksJsonObject = docs.getJSONObject(i)
                books.add(getBook(booksJsonObject))
            }
            return BookSearchResponse(books)
        }
    }
}