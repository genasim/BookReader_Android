package genadimk.bookreader.viewmodels

import android.net.Uri
import androidx.lifecycle.*
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookDao
import genadimk.bookreader.model.BookEntry
import kotlinx.coroutines.*

class HomeViewModel(private val bookDao: BookDao) :
    ViewModel() {

    val allBookEntriesLive: LiveData<List<BookEntry>> = bookDao.getAllBooks().asLiveData()
    private val bookList: MutableList<Book> = mutableListOf()

    fun getBookList() = bookList.toList()

    fun updateBookList(bookEntries: List<BookEntry>): List<Book> {
        val newList = mutableListOf<Book>()
        bookEntries.forEach { newList.add(Book.Builder(it).build()) }

        bookList.apply {
            clear()
            addAll(newList)
        }

        return newList
    }

    fun noBooksAreChecked(): Boolean =
        bookList.all { it.card?.isChecked == false }

    fun addBook(uri: Uri?) {
        uri?.let {
            val newEntry = createNewBookEntry(it)
            insert(newEntry)
        }
    }

    private fun createNewBookEntry(uri: Uri): BookEntry = BookEntry(
        name = uri.lastPathSegment.toString(),
        uri = uri.toString(),
        page = 0,
        current = 0
    )


    fun checkCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 1)
        update(updatedBook)
    }

    fun uncheckCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 0)
        update(updatedBook)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun removeBook(book: Book) {
        val result = viewModelScope.async { getItem(book.id) }
        result.invokeOnCompletion {
            if (it == null)
                delete(result.getCompleted())
        }
    }

    private fun insert(book: BookEntry) = viewModelScope.launch {
        bookDao.insert(book)
    }

    private fun update(book: BookEntry) = viewModelScope.launch {
        bookDao.update(book)
    }

    private fun delete(book: BookEntry) = viewModelScope.launch {
        bookDao.delete(book)
    }

    private suspend fun getItem(id: Int) = withContext(Dispatchers.IO) {
        bookDao.getBook(id)
    }
}


/**
 * Factory class to instantiate the [HomeViewModel] instance.
 */
class HomeViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(bookDao) as T
        }
        throw IllegalArgumentException("View model not inherited from [HomeViewModel]")
    }
}