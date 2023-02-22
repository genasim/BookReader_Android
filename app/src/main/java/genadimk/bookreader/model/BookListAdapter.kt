package genadimk.bookreader.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.databinding.BookListItemBinding

class BookListAdapter(
    private val onItemClicked: (Books) -> Unit,
    private val onItemLongClicked: (Books, View) -> Boolean,
) : ListAdapter<Books, BookListAdapter.BookViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            BookListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClicked, onItemLongClicked)
    }

    class BookViewHolder(private var binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            book: Books,
            onItemClicked: (Books) -> Unit,
            onItemLongClicked: (Books, View) -> Boolean,
        ) {
            binding.apply {
                bookName.text = book.name
                bookItemCard.setOnClickListener { onItemClicked(book) }
                bookItemCard.setOnLongClickListener { onItemLongClicked(book, itemView) }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Books>() {
            override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
                return oldItem.uri == newItem.uri && oldItem.page == newItem.page
            }
        }
    }
}