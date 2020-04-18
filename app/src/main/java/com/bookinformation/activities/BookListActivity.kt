package com.bookinformation.activities

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.bookinformation.adapter.BookAdapter
import com.bookinformation.api_client.BookApiClient
import com.bookinformation.models.Book
import com.bookinformation.models.BookSearchResponse
import com.example.bookinformation.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.ArrayList

class BookListActivity : AppCompatActivity() {
    private lateinit var lvBooks: ListView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val books = ArrayList<Book>()
        bookAdapter = BookAdapter(this, books)

        lvBooks = findViewById(R.id.lv_books)
        lvBooks.adapter = bookAdapter

        val bookName = intent?.extras?.getString("bookName").toString()
        BookApiClient().searchBooks(bookName, callback())
    }

    private fun callback(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { return displayBooks(it) }
            }

            private fun displayBooks(response: String) {
                val bookSearchResponse = BookSearchResponse.getBookSearchResponse(response)
                val books = bookSearchResponse.books

                runOnUiThread {
                    run {
                        bookAdapter.clear()
                        for (book in books) {
                            bookAdapter.add(book)
                        }
                        bookAdapter.notifyDataSetChanged()

                    }
                }

            }
        }
    }
}
