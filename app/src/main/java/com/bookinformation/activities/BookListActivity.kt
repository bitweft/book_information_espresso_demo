package com.bookinformation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
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
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        val books = ArrayList<Book>()
        bookAdapter = BookAdapter(this, books)

        progressBar = findViewById(R.id.progress_bar)
        lvBooks = findViewById(R.id.lv_books)
        lvBooks.adapter = bookAdapter

        val bookName = intent?.extras?.getString("bookName").toString()

        progressBar.visibility = ProgressBar.VISIBLE
        BookApiClient().searchBooks(bookName, callback())

        lvBooks.setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
            selectBook(position)
        }
    }

    private fun selectBook(position: Int) {
        val intent = Intent(this, BookDetailActivity::class.java).apply {
            putExtra("bookId", bookAdapter.getItem(position)?.bookId.toString())
        }
        startActivity(intent)
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
                        progressBar.visibility = ProgressBar.GONE
                    }
                }

            }
        }
    }
}
