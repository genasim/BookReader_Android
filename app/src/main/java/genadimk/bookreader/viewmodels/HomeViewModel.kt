package genadimk.bookreader.viewmodels

import androidx.lifecycle.*
import genadimk.bookreader.model.BookDao
import genadimk.bookreader.model.Books
import kotlinx.coroutines.launch

class HomeViewModel(private val bookDao: BookDao) : ViewModel() {

    val allBookEntries: LiveData<List<Books>> = bookDao.getAllBooks().asLiveData()

    fun addBook() {
        val newBook = createNewBookEntry("Clean code")
        viewModelScope.launch {
            bookDao.insert(newBook)
        }
    }

    private fun createNewBookEntry(name: String): Books = Books(
        name = name,
        uri = "",
        page = 0
    )

}

class HomeViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(bookDao) as T
        }
        throw IllegalArgumentException("View model not inherited from [HomeViewModel]")
    }
}