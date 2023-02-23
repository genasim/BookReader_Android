package genadimk.bookreader.viewmodels

import androidx.lifecycle.*
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookDao
import genadimk.bookreader.model.BookEntry
import kotlinx.coroutines.launch

class HomeViewModel(private val bookDao: BookDao) :
    ViewModel() {

    val allBookEntriesLive: LiveData<List<BookEntry>> = bookDao.getAllBooks().asLiveData()
    private val bookList: MutableList<Book> = mutableListOf()

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

    fun addBook() {
        val newEntry = createNewBookEntry("Clean Code")
        insert(newEntry)
    }

    private fun createNewBookEntry(name: String): BookEntry = BookEntry(
        name = name,
        uri = "",
        page = 0,
        current = 0
    )

    private fun insert(book: BookEntry) = viewModelScope.launch {
        bookDao.insert(book)
    }

    fun checkCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 1)
        update(updatedBook)
    }

    fun uncheckCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 0)
        update(updatedBook)
    }

    private fun update(book: BookEntry) = viewModelScope.launch {
        bookDao.update(book)
    }


    fun delete(book: BookEntry) = viewModelScope.launch {
        bookDao.delete(book)
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