package genadimk.bookreader.ui.floatingButton

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.observer.Observer
import genadimk.bookreader.ui.home.HomeFragment

class ButtonAdd :
    ButtonHandler {

    init {
        HomeFragment.callback = this::addItem
    }

    companion object {
        lateinit var fragment: HomeFragment
    }

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
        launchGetIntent()
    }

    private fun launchGetIntent() = fragment.getContent.launch("application/pdf")

    private fun addItem(uri: Uri?) {
        with(adapter as BookListViewAdapter) {
            val newBook = BookRepository.createBookItem(uri)
            notifyItemInserted(BookRepository.addItem(newBook))
        }
    }
}