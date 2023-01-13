package genadimk.bookreader.ui.home.booklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R
import genadimk.bookreader.ui.floatingButton.AppFloatingButton
import genadimk.bookreader.ui.floatingButton.ButtonAdder
import genadimk.bookreader.ui.floatingButton.ButtonRemove

class BookListViewAdapter :
    RecyclerView.Adapter<BookListViewAdapter.ItemViewHolder>() {

    val data
        get() = BookDataList.data

    //  Subscribe class to different broadcasters
    init {
        ButtonRemove.subscribe(this)
        ButtonAdder.subscribe(this::notifyDataSetChanged)
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

        item.bookCard.setOnLongClickListener {
            item.bookCard.toggle()
            AppFloatingButton.updateButton(ButtonRemove)
            true
        }

        item.bookCard.setOnClickListener {
            // TODO: open reading fragment
        }
    }

    override fun getItemCount(): Int = data.size

}
