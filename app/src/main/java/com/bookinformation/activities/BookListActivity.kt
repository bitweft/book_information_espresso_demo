package com.bookinformation.activities

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var progressBar: ProgressBar
    private val books = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        bookAdapter = BookAdapter(books)

        recyclerView = findViewById(R.id.books_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookAdapter

        val bookName = intent?.extras?.getString("bookName").toString()

        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = ProgressBar.VISIBLE
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
                val booksResponse = bookSearchResponse.books

                runOnUiThread {
                    for (book in booksResponse) {
                        books.add(book)
                    }
                    bookAdapter.notifyItemInserted(books.size - 1)
                    progressBar.visibility = ProgressBar.GONE
                }

            }
        }
    }
}
