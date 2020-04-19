package com.bookinformation.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bookinformation.models.Book
import com.example.bookinformation.R
import com.squareup.picasso.Picasso


class BookAdapter(private val context: Activity, private val books: List<Book>) :
    ArrayAdapter<Book>(context, R.layout.book_result_detail, books) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.book_result_detail, null, true)

        val coverImage = rowView.findViewById(R.id.book_image) as ImageView
        val title = rowView.findViewById(R.id.title) as TextView
        val author = rowView.findViewById(R.id.author) as TextView

        Picasso.get().load(books[position].coverUrl).into(coverImage)
        title.text = books[position].title
        author.text = books[position].author

        return rowView
    }
}