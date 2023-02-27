package genadimk.bookreader.viewmodels

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.*
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookEntry
import genadimk.bookreader.model.BookRepository
import kotlinx.coroutines.*
import java.io.File

class HomeViewModel(private val repository: BookRepository) :
    ViewModel() {
    private val dao = repository.database.getBookDao()

    val allBookEntriesLive: LiveData<List<BookEntry>> = dao.getAllBooks().asLiveData()
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

    fun addBook(uri: Uri, contentResolver: ContentResolver) {
        val filename = getFilename(contentResolver, uri)
        filename?.let {
            val newEntry = createNewBookEntry(uri, filename)
            insert(newEntry)
        }
    }

    private fun createNewBookEntry(uri: Uri, name: String): BookEntry =
        createUpdatedBookEntry(name = name, uri = uri.toString())

    private fun getFilename(contentResolver: ContentResolver, uri: Uri): String? {
        return when (uri.scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    cursor.moveToFirst()
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.getString(nameIndex);
                }
            }
            ContentResolver.SCHEME_FILE -> {
                uri.path?.let { path ->
                    File(path).name
                }
            }
            else -> null
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateCurrentBook(book: Book) {
        if (repository.currentBook == book)
            return

        repository.currentBook = book

//        val result = viewModelScope.async { getCurrent() }
//        result.invokeOnCompletion {
//            if (it == null)
//                uncheckCurrent(result.getCompleted())
//        }
//
//        with(book) {
//            val entryToUpdate = createUpdatedBookEntry(name, uri.toString(), page, 1)
//            checkCurrent(entryToUpdate)
//        }

    }

    private fun checkCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 1)
        update(updatedBook)
    }

    private fun uncheckCurrent(book: BookEntry) {
        val updatedBook = book.copy(current = 0)
        update(updatedBook)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun removeBook(book: Book) {
        val result = viewModelScope.async { getItem(book) }
        result.invokeOnCompletion {
            if (it == null)
                delete(result.getCompleted())
        }
    }

    private fun createUpdatedBookEntry(
        name: String = "",
        uri: String = "",
        page: Int = 0,
        current: Int = 0,
    ): BookEntry = BookEntry(
        name = name,
        uri = uri,
        page = page,
        current = current
    )

    private fun insert(entry: BookEntry) = viewModelScope.launch {
        dao.insert(entry)
    }

    private fun update(entry: BookEntry) = viewModelScope.launch {
        dao.update(entry)
    }

    private fun delete(entry: BookEntry) = viewModelScope.launch {
        dao.delete(entry)
    }

    private suspend fun getItem(book: Book) = withContext(Dispatchers.IO) {
        dao.getBook(book.id)
    }

    private suspend fun getCurrent() = withContext(Dispatchers.IO) {
        dao.getCurrentBook()
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