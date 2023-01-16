package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList
import java.util.function.Predicate

class ButtonRemove(val data: MutableList<Book> = BookDataList.data) :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        removeCheckedItems()
    }

    fun removeCheckedItems() {
//        Log.i(TAG, "BEFORE  -> button data size: ${data.size} | adapter data size: ${adapter.itemCount}")
        val predicate = Predicate { bookItem: Book -> bookItem.isChecked }
        data.filter { predicate.test(it) }
            .forEach { removeItem(it) }

        AppFloatingButton.apply { buttonHandler = buttonAdder }
//        Log.i(TAG, "AFTER   -> button data size: ${data.size} | adapter data size: ${adapter.itemCount}")
    }

    fun removeItem(item: Book) {
        val position = data.indexOf(item)
        data.remove(item)
        adapter.notifyItemRemoved(position)
    }
}