package genadimk.bookreader

import androidx.multidex.MultiDexApplication
import genadimk.bookreader.model.BookRepository

class BookReaderApplication : MultiDexApplication() {

    val repository: BookRepository = BookRepository(this)

}