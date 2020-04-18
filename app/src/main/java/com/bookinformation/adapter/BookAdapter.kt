package com.bookinformation.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bookinformation.models.Book
import com.example.bookinformation.R


class BookAdapter(private val context: Activity, private val books: List<Book>) :
    ArrayAdapter<Book>(context, R.layout.book_result_detail, books) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.book_result_detail, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView

        titleText.text = books[position].title

        return rowView
    }
}