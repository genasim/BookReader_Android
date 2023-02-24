package genadimk.bookreader.view.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.viewmodels.HomeViewModel
import java.util.function.Predicate

class ButtonRemove(
    private val viewModel: HomeViewModel,
) : ButtonHandler {

    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        removeCheckedItems()
    }

    private fun removeCheckedItems() {
        val predicate = Predicate { bookItem: Book -> bookItem.isChecked }
        viewModel.getBookList()
            .filter { predicate.test(it) }
            .forEach { removeItem(it) }

        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    private fun removeItem(book: Book) {
        viewModel.removeBook(book)
    }
}