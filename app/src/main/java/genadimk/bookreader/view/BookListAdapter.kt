package genadimk.bookreader.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.databinding.BookListItemBinding
import genadimk.bookreader.model.Book

class BookListAdapter(
    private val onItemClicked: (Book) -> Unit,
    private val onItemLongClicked: (Book) -> Boolean,
    private val onEditClicked: (Book) -> Unit,
) : ListAdapter<Book, BookListAdapter.BookViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClicked, onItemLongClicked, onEditClicked)
    }

    class BookViewHolder(private var binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            book: Book,
            onItemClicked: (Book) -> Unit,
            onItemLongClicked: (Book) -> Boolean,
            onEditClicked: (Book) -> Unit,
        ) {
            binding.apply {
                bookName.text = book.name
                bookCoverImage.setImageBitmap(book.cover)
                book.card = bookItemCard
                book.card?.isChecked = false
                bookItemCard.setOnClickListener { onItemClicked(book) }
                bookItemCard.setOnLongClickListener { onItemLongClicked(book) }
                editButton.setOnClickListener { onEditClicked(book) }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.uri == newItem.uri && oldItem.page == newItem.page
            }
        }
    }
}