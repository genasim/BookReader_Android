package genadimk.bookreader.ui.floatingButton

import android.util.Log
import genadimk.bookreader.R
import genadimk.bookreader.TAG
import genadimk.bookreader.ui.home.booklist.Book
import genadimk.bookreader.ui.home.booklist.BookDataList
import java.util.function.Predicate

object BookRemover : BookHandler {
    override val imageRes: Int
        get() = R.drawable.ic_delete

    override fun clickButton() {
        Log.d(TAG, "removeItems")
        val predicate = Predicate { bookCard: Book -> bookCard.isChecked}
        BookDataList.data.filter { predicate.test(it) }.forEach { BookDataList.data.remove(it) }
    }
}