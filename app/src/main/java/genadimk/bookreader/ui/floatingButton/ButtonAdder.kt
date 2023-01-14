package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList
import genadimk.bookreader.ui.home.booklist.BookDataList.data

object ButtonAdder :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem(Book())
    }

    private fun addItem(item: Book) {
        data.add(item)
        adapter.notifyItemInserted(data.size)
    }
}