package genadimk.bookreader.model

import android.app.Application
import genadimk.bookreader.booklist.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(private val application: Application) {
    val database: BookDatabase by lazy { BookDatabase.getDatabase(application) }

    var currentBook: Book? = null

    suspend fun insert(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.insert(entry)
    }

    suspend fun update(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.update(entry)
    }

    suspend fun delete(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.delete(entry)
    }

    suspend fun getItem(book: Book) = withContext(Dispatchers.IO) {
        database.bookDao.getBook(book.id)
    }

    suspend fun getCurrent() = withContext(Dispatchers.IO) {
        database.bookDao.getCurrentBook()
    }
}