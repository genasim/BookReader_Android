package genadimk.bookreader.ui.floatingButton

import androidx.recyclerview.widget.RecyclerView
import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.utils.NEW_BOOK_KEY
import genadimk.bookreader.observer.CallbackProxy
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.observer.Observer
import genadimk.bookreader.ui.home.HomeFragment

class ButtonAdd :
    ButtonHandler, Observer {

    init {
        HomeFragment.subscribe(this)
    }

    lateinit var adapter: RecyclerView.Adapter<*>

    override val imageRes: Int
        get() = R.drawable.ic_add

    override fun clickButton() {
//        requestPermission()
        HomeFragment.contentPicker.launch("application/pdf")
    }

    private fun addItem(newBook: Book) {
        with(adapter as BookListViewAdapter) {
            notifyItemInserted(BookRepository.addItem(newBook))
        }
    }

    override fun update(args: Observable.Arguments?) {
        args?.let {
            if (!it.args.containsKey(NEW_BOOK_KEY) || args[NEW_BOOK_KEY] !is Book)
                return

            val newBook = args[NEW_BOOK_KEY] as Book
            addItem(newBook)
        }
    }
}