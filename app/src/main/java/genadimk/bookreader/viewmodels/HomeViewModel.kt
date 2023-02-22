package genadimk.bookreader.viewmodels

import android.view.View
import androidx.lifecycle.*
import com.google.android.material.card.MaterialCardView
import genadimk.bookreader.model.BookDao
import genadimk.bookreader.model.Books
import kotlinx.coroutines.launch

class HomeViewModel(private val bookDao: BookDao) :
    ViewModel() {

    val allBookEntriesLive: LiveData<List<Books>> = bookDao.getAllBooks().asLiveData()

    fun addBook() {
        val newBook = createNewBookEntry("Clean code")
        insert(newBook)
    }

    private fun createNewBookEntry(name: String): Books = Books(
        name = name,
        uri = "",
        page = 0,
        checked = 0
    )

    private fun insert(book: Books) = viewModelScope.launch {
        bookDao.insert(book)
    }

    fun checkCurrent(book: Books, view: View) {
        val updatedBook = book.copy(checked = 1)
        update(updatedBook)
        (view as MaterialCardView).isChecked = true
    }

    fun uncheckCurrent(book: Books, view: View) {
        val updatedBook = book.copy(checked = 0)
        update(updatedBook)
        (view as MaterialCardView).isChecked = false
    }

    private fun createCopyEntry(
        book: Books,
        name: String = book.name,
        uri: String = book.uri,
        page: Int = book.page,
        checked: Int = book.checked,
    ) = Books(book.id, name, uri, page, checked)

    private fun update(book: Books) = viewModelScope.launch {
        bookDao.update(book)
    }

    fun delete(book: Books) = viewModelScope.launch {
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