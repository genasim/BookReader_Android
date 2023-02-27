package genadimk.bookreader.model

import android.app.Application
import genadimk.bookreader.booklist.Book

class BookRepository(private val application: Application) {
    val database: BookDatabase by lazy { BookDatabase.getDatabase(application) }

    var currentBook: Book? = null
}