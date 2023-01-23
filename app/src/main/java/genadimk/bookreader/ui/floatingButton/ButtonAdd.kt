package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.BookData
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.BookRepository

class ButtonAdd :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>
    private val bookData: BookData = BookRepository

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem()
    }

    private fun addItem() {
        with(adapter as BookListViewAdapter) {
            notifyItemInserted(bookData.addItem())
            parent.scrollToPosition(0)
        }
    }
}