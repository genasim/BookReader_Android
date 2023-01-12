package genadimk.bookreader.ui.floatingButton

import genadimk.bookreader.R
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList
import java.util.function.Predicate

object ButtonRemover : ButtonHandler {
    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        val predicate = Predicate { bookItem: Book -> bookItem.bookCard!!.isChecked }
        BookDataList.data
            .filter { predicate.test(it) }
            .forEach { BookDataList.data.remove(it) }
    }

}