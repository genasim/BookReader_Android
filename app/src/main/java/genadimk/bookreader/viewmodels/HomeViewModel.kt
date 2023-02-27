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

    val allBookEntriesLive: LiveData<List<BookEntry>> =
        repository.database.bookDao.getAllBooks().asLiveData()

    private val _bookList: MutableList<Book> = mutableListOf()
    fun getBookList() = _bookList.toList()

    fun updateBookList(bookEntries: List<BookEntry>): List<Book> {
        val newList = mutableListOf<Book>()
        bookEntries.forEach { newList.add(Book.Builder(it).build()) }

        _bookList.apply {
            clear()
            addAll(newList)
        }

        return newList
    }

    fun noBooksAreChecked(): Boolean =
        _bookList.all { it.card?.isChecked == false }

    fun addBook(uri: Uri, contentResolver: ContentResolver) = viewModelScope.launch {
        val filename = getFilename(contentResolver, uri)
        filename?.let {
            val newEntry = repository.createNewBookEntry(uri, filename)
            repository.insert(newEntry)
        }
    }

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

    fun removeBook(book: Book) = runBlocking {
        val result = repository.getItem(book)
        repository.delete(result)
    }

    fun updateCurrentBook(newBook: Book) = viewModelScope.launch {
        val oldBook = getBookList().firstOrNull { it.current == 1}
        repository.updateCurrentBook(newBook, oldBook)
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