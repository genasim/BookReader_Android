package genadimk.bookreader.ui.home.booklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.R

class BookListViewAdapter() : RecyclerView.Adapter<BookListViewAdapter.ItemViewHolder>() {

    private val data = BookDataList.data

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


            true
        }
    }

    override fun getItemCount(): Int = data.size
}