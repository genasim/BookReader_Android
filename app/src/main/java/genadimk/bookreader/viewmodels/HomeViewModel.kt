package genadimk.bookreader.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import genadimk.bookreader.model.Book
import genadimk.bookreader.model.BookRepository
import genadimk.bookreader.model.room.BookEntry
import genadimk.bookreader.utils.asBookEntry
import kotlinx.coroutines.*

class HomeViewModel(private val repository: BookRepository) :
    ViewModel() {

    val allBookEntriesLive: LiveData<List<BookEntry>> =
        repository.getBookEntries().asLiveData()

    private val _bookList: MutableList<Book> = mutableListOf()
    fun getBookList() = _bookList.toList()

    /**
     * Creates a new list of books to be used for displaying, using
     * the given [BookEntry] list
     * @return the newly created list of books
     * */
    fun updateBookList(bookEntries: List<BookEntry>, context: Context): List<Book> {
        val newList = mutableListOf<Book>()
        bookEntries.forEach { newList.add(Book(it, context)) }
        _bookList.apply {
            clear()
            addAll(newList)
        }

        return newList
    }

    fun noBooksAreChecked(): Boolean =
        _bookList.all { it.card?.isChecked == false }

    /**
     * Add a new [BookEntry] created from the given uri
     * @param uri address of the book on the device
     * @param filename name for the book
     * @return whether a duplicate was found
     * */
    fun addBook(uri: Uri, filename: String?) = runBlocking {
        var result = true
        filename?.let {
            val newEntry = repository.createNewBookEntry(uri, it)
            val entry = repository.getItem(newEntry)
            when (entry == null) {
                true -> repository.insert(newEntry)
                false -> result = false
            }
        }
        result
    }

    fun removeBook(book: Book) = runBlocking {
        if (book.current == 1)
            repository.updateCurrentBook(null, null)

        val result = repository.getItem(book.asBookEntry())
        repository.delete(result!!)
    }

    fun updateCurrentBook(newBook: Book) = viewModelScope.launch {
        val oldBook = getBookList().firstOrNull { it.current == 1 }
        repository.updateCurrentBook(newBook, oldBook)
    }

    fun updateBook(entry: BookEntry) = viewModelScope.launch {
        repository.update(entry)
    }
}


/**
 * Factory class to instantiate the [HomeViewModel] instance.
 */
class HomeViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("View model not inherited from [HomeViewModel]")
    }
}