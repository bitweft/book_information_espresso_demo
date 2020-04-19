package com.bookinformation.models

import org.json.JSONObject

data class BookDetail(
    val title: String,
    val author: String,
    val bookId: String,
    val coverUrl: String,
    val numberOfPages: String,
    val publishDate: String
) {
    companion object {
        fun getBookDetail(bookId: String, res: String): BookDetail {
            val response = JSONObject(res)
            val title = response.getString("title")
            val publishDate = response.getString("publish_date")
            val coverUrl = "http://covers.openlibrary.org/b/olid/$bookId-L.jpg?default=false"
            val author = getAuthor(response)
            val numberOfPages = getNumberOfPagesInBook(response)

            return BookDetail(title, author, bookId, coverUrl, numberOfPages, publishDate)
        }

        private fun getNumberOfPagesInBook(response: JSONObject): String {
            var numberOfPages = "unknown"
            if (response.has("number_of_pages")) {
                numberOfPages = response.getInt("number_of_pages").toString()
            }
            return numberOfPages
        }

        private fun getAuthor(response: JSONObject): String {
            var author = "unknown"
            if (response.has("by_statement")) {
                author = response.getString("by_statement")
            }
            return author
        }
    }
}