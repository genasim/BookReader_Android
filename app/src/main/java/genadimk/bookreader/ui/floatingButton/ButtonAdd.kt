package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList.data

class ButtonAdd :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>
    @Deprecated("Temporary usage for new items' name; items will have different names regardless")
    private var count: Int = 0

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        addItem(Book(bookName = count++.toString()))
    }

    private fun addItem(item: Book) {
        data.add(item)
        adapter.notifyItemInserted(data.size)
    }
}