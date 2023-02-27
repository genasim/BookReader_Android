package genadimk.bookreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookDao
import genadimk.bookreader.model.BookEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReadviewViewModel(private val bookDao: BookDao) : ViewModel() {

    private val _currentBook: MutableLiveData<BookEntry> = MutableLiveData()
    val currentBook: LiveData<BookEntry> = _currentBook

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCurrent() {
        val result = viewModelScope.async { getCurrentBook() }
        result.invokeOnCompletion {
            if (it == null)
                _currentBook.postValue(result.getCompleted())
        }
    }

    private suspend fun getCurrentBook() = withContext(Dispatchers.IO) {
        bookDao.getCurrentBook()
    }
}

/**
 * Factory class to instantiate the [ReadviewViewModel] instance.
 */
class ReadviewViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReadviewViewModel(bookDao) as T
        }
        throw IllegalArgumentException("View model not inherited from [ReadviewViewModel]")
    }
}