package com.example.bookinformation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class HomeActivity : AppCompatActivity() {
    lateinit var enterButton: Button
    lateinit var userName: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userName = findViewById(R.id.user_name)
        enterButton = findViewById(R.id.enter_books_button)

        userName.addTextChangedListener { enableButton() }
        enterButton.setOnClickListener { enterBooksList() }
    }

    private fun enableButton() {
        enterButton.isEnabled = userName.text.trim().isNotEmpty()
    }

    private fun enterBooksList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
