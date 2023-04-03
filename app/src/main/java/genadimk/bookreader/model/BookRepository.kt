package genadimk.bookreader.model

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import genadimk.bookreader.model.room.BookDatabase
import genadimk.bookreader.model.room.BookEntry
import genadimk.bookreader.utils.asBookEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(private val application: Application) {
    private val database: BookDatabase by lazy { BookDatabase.getDatabase(application) }

    private val _currentBook = MutableLiveData<Book?>()
    val currentBook: LiveData<Book?> = _currentBook

    suspend fun updateCurrentBook(newBook: Book?, oldBook: Book?) = withContext(Dispatchers.IO) {
        when (newBook) {
            null -> _currentBook.postValue(null)
            else -> {
                newBook.current = 1
                _currentBook.postValue(newBook)
                val newEntry = newBook.asBookEntry()
                update(newEntry)
            }
        }

        oldBook?.let {
            it.current = 0
            val oldEntry = it.asBookEntry()
            update(oldEntry)
        }
    }

    fun createNewBookEntry(uri: Uri, name: String): BookEntry = BookEntry(
        name = name,
        uri = uri.toString(),
        page = 0,
        current = 0
    )

    fun getBookEntries() = database.bookDao.getAllBooks()

    suspend fun insert(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.insert(entry)
    }

    suspend fun update(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.update(entry)
    }

    suspend fun delete(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.delete(entry)
    }

    suspend fun getItem(entry: BookEntry) = withContext(Dispatchers.IO) {
        database.bookDao.getBook(entry.uri)
    }

    suspend fun getCurrent() = withContext(Dispatchers.IO) {
        database.bookDao.getCurrentBook()
    }
}