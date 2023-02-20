package genadimk.bookreader.view.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.Repository
import java.util.function.Predicate

class ButtonRemove(
    private val bookData: Repository,
) :
    ButtonHandler {

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        removeCheckedItems()
    }

    private fun removeCheckedItems() {
        val predicate = Predicate { bookItem: Book -> bookItem.isChecked }
        bookData.getRepository()
            .filter { predicate.test(it) }
            .forEach { removeItem(it) }

        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    private fun removeItem(item: Book) {
        with(adapter as BookListViewAdapter) {
            notifyItemRemoved(bookData.removeItem(item))
        }
    }
}