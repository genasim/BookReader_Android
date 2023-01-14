package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList.data
import genadimk.bookreader.ui.home.booklist.BookListViewAdapter
import java.util.function.Predicate

object ButtonRemove :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
       removeCheckedItems()
       AppFloatingButton.updateButton(ButtonAdder)
    }

    private fun removeCheckedItems() {
       val predicate = Predicate { bookItem: Book -> bookItem.bookCard.isChecked }
       data.filter { predicate.test(it) }
           .forEach { removeItem(it) }
    }

    private fun removeItem(item: Book) {
       val position = data.indexOf(item)
       data.remove(item)
       adapter.notifyItemRemoved(position)
    }
}