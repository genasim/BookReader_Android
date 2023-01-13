package genadimk.bookreader.ui.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.observer.Broadcaster
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookListViewAdapter
import java.util.function.Predicate

object ButtonRemove :
    ButtonHandler,
    Observable by Broadcaster() {

    private lateinit var adapter: BookListViewAdapter

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        removeCheckedItems()
        AppFloatingButton.updateButton(ButtonAdder)
    }

    fun subscribe(listAdapter: BookListViewAdapter) {
        adapter = listAdapter
    }

    private fun removeCheckedItems() {
        val predicate = Predicate { bookItem: Book -> bookItem.bookCard.isChecked }
        adapter.data.filter { predicate.test(it) }
                    .forEach { removeItem(it) }
    }

    private fun removeItem(item: Book) {
        val position = adapter.data.indexOf(item)
        adapter.data.remove(item)
        adapter.notifyItemRemoved(position)
    }
}