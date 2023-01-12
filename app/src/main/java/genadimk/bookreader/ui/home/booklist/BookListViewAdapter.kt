package genadimk.bookreader.ui.home.booklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R
import genadimk.bookreader.observer.Observer
import genadimk.bookreader.ui.floatingButton.AppFloatingButton
import genadimk.bookreader.ui.floatingButton.ButtonAdder
import genadimk.bookreader.ui.floatingButton.ButtonRemover
import java.util.function.Predicate

class BookListViewAdapter :
    RecyclerView.Adapter<BookListViewAdapter.ItemViewHolder>(),
    Observer {

    private val data = BookDataList.data

    //  Subscribe class to different broadcasters
    init {
        ButtonRemover.subscribe(this)
        ButtonAdder.subscribe(this)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookCard: MaterialCardView = view.findViewById(R.id.book_item_card) as MaterialCardView
        val bookCover: ImageView = view.findViewById(R.id.book_cover_image) as ImageView
        val bookName: TextView = view.findViewById(R.id.book_name) as TextView
        val bookAuthor: TextView = view.findViewById(R.id.book_author_name) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolderLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return ItemViewHolder(viewHolderLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            item.bookCard = this.bookCard
            bookCover.setImageResource(item.bookCover)
            bookName.text = item.bookName
            bookAuthor.text = item.bookAuthor
        }

        item.bookCard!!.setOnLongClickListener {
            item.bookCard!!.toggle()
            AppFloatingButton.updateButton(ButtonRemover)
            true
        }

        item.bookCard!!.setOnClickListener {
            // TODO: open reading fragment
        }
    }

    override fun getItemCount(): Int = data.size

    override fun update() {
        removeCheckedItems()
    }

    private fun removeCheckedItems() {
        val predicate = Predicate { bookItem: Book -> bookItem.bookCard!!.isChecked }
        BookDataList.data
            .filter { predicate.test(it) }
            .forEach { removeItemFromView(it) }
    }

    private fun removeItemFromView(model: Book) {
        val position = data.indexOf(model)
        data.remove(model)
        notifyItemRemoved(position)
    }
}
