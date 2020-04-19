package com.bookinformation.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bookinformation.api_client.BookApiClient
import com.bookinformation.models.BookDetail
import com.example.bookinformation.R
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class BookDetailActivity : AppCompatActivity() {
    lateinit var bookImage: ImageView
    lateinit var title: TextView
    lateinit var author: TextView
    lateinit var publishDate: TextView
    lateinit var numOfPages: TextView
    private lateinit var bookId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        bookImage = findViewById(R.id.book_image)
        title = findViewById(R.id.title)
        author = findViewById(R.id.author)
        publishDate = findViewById(R.id.publish_date)
        numOfPages = findViewById(R.id.num_of_pages)

        bookId = intent?.extras?.getString("bookId").toString()

        BookApiClient().getBookDetails(bookId, callback())
    }

    private fun callback(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { return displayBookDetails(it) }
            }

            private fun displayBookDetails(response: String) {
                val bookDetail = BookDetail.getBookDetail(bookId, response)

                runOnUiThread {
                    run {
                        Picasso.get().load(bookDetail.coverUrl)
                            .fit()
                            .placeholder(R.drawable.empty_book_detail)
                            .into(bookImage)
                        title.text = bookDetail.title
                        author.text = bookDetail.author
                        publishDate.text = bookDetail.publishDate
                        numOfPages.text = bookDetail.numberOfPages
                    }
                }
            }
        }
    }
}
