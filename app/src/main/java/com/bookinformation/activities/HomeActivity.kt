package com.bookinformation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bookinformation.R

class HomeActivity : AppCompatActivity() {
    private lateinit var searchButton: Button
    private lateinit var bookName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bookName = findViewById(R.id.book_name)
        searchButton = findViewById(R.id.search_books)

        bookName.addTextChangedListener { conditionallyEnableButton() }
        searchButton.setOnClickListener { enterBooksList() }
    }

    private fun conditionallyEnableButton() {
        searchButton.isEnabled = bookName.text.trim().isNotEmpty()
    }

    private fun enterBooksList() {
        val bookName = bookName.text.toString()
        val intent = Intent(this, BookListActivity::class.java).apply {
            putExtra("bookName", bookName)
        }
        startActivity(intent)
    }
}
