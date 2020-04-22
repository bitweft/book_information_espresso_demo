package com.bookinformation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookinformation.activities.BookDetailActivity
import com.bookinformation.models.Book
import com.example.bookinformation.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.book_result_detail.view.*


class BookAdapter(private val books: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_result_detail, parent, false)
        return BookHolder(inflatedView)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = books[position]
        holder.bindBook(book)
    }

    class BookHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var book: Book? = null

        fun bindBook(book: Book) {
            this.book = book

            Picasso.get().load(book.coverUrl)
                .fit()
                .placeholder(R.drawable.empty_book_result)
                .into(view.book_image)
            view.title.text = book.title
            view.author.text = book.author
        }

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context, BookDetailActivity::class.java).apply {
                putExtra("bookId", book?.bookId)
            }
            context.startActivity(intent)
        }
    }
}