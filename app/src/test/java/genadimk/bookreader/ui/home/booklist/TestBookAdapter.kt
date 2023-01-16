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

class TestBookAdapter(private val data: List<Book>) :
    RecyclerView.Adapter<TestBookAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookCard: MaterialCardView = view.findViewById(R.id.book_item_card) as MaterialCardView
        val bookCover: ImageView = view.findViewById(R.id.book_cover_image) as ImageView
        val bookName: TextView = view.findViewById(R.id.book_name) as TextView
        val bookAuthor: TextView = view.findViewById(R.id.book_author_name) as TextView
        var isChecked: Boolean = false
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
            item.bookCard?.isChecked = false
            bookCover.setImageResource(item.bookCover)
            bookName.text = item.bookName
            bookAuthor.text = item.bookAuthor
            isChecked = item.isChecked
        }

        item.bookCard?.setOnLongClickListener {
            item.apply { isChecked = !isChecked }
            item.bookCard?.toggle()
            AppFloatingButton.apply { buttonHandler = buttonRemover }

            if (!BookDataList.data.any { it.bookCard?.isChecked == true })
                AppFloatingButton.apply { buttonHandler = buttonAdder }

            true
        }

        item.bookCard?.setOnClickListener {
            // TODO: open reading fragment
        }
    }

    override fun getItemCount(): Int = data.size
}
