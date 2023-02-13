package genadimk.bookreader.booklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.MobileNavigationDirections
import genadimk.bookreader.R
import genadimk.bookreader.ui.floatingButton.AppFloatingButton

class BookListViewAdapter :
    RecyclerView.Adapter<BookListViewAdapter.ItemViewHolder>() {

    private val data
        get() = BookRepository.getRepository()

    lateinit var parent: RecyclerView
        private set

    init {
        AppFloatingButton.buttonRemover.adapter = this
        AppFloatingButton.buttonAdder.adapter = this
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val bookCard: MaterialCardView = view.findViewById(R.id.book_item_card) as MaterialCardView
        val bookCover: ImageView = view.findViewById(R.id.book_cover_image) as ImageView
        val bookName: TextView = view.findViewById(R.id.book_name) as TextView
        val bookAuthor: TextView = view.findViewById(R.id.book_author_name) as TextView
        var isChecked: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolderLayout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return ItemViewHolder(viewHolderLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val book = data[position]
        holder.apply {
            book.card = this.bookCard
            book.card?.isChecked = false
            bookCover.setImageResource(book.cover)
            bookName.text = book.name
            bookAuthor.text = book.author
            isChecked = book.isChecked
        }

        setLongClickListener(book)

        setClickListener(book, holder)
    }

    private fun setLongClickListener(item: Book) {
        item.card?.setOnLongClickListener {
            item.apply {
                isChecked = !isChecked
                card?.isChecked = isChecked
            }
            AppFloatingButton.apply { buttonHandler = buttonRemover }

            if (data.all { it.card?.isChecked == false })
                AppFloatingButton.apply { buttonHandler = buttonAdder }

            true
        }
    }

    private fun setClickListener(item: Book, holder: ItemViewHolder) {
        item.card?.setOnClickListener {
            val action: NavDirections = MobileNavigationDirections.actionGlobalNavReadview()
            BookRepository.currentBook = item
            holder.view.findNavController().navigate(action)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        parent = recyclerView
    }

    override fun getItemCount(): Int = data.size

}