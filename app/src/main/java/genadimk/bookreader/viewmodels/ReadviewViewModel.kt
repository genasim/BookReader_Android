package genadimk.bookreader.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookRepository

class ReadviewViewModel(private val repository: BookRepository) : ViewModel() {

    private val dao = repository.database.getBookDao()

    private val _currentBook: MutableLiveData<Book> = MutableLiveData()
    val currentBook: LiveData<Book> = _currentBook

    //    @OptIn(ExperimentalCoroutinesApi::class)
//    fun fetchCurrent() {
//        val result = viewModelScope.async { getCurrentBook() }
//        result.invokeOnCompletion {
//            if (it == null)
//                _currentBook.postValue(result.getCompleted())
//        }
//    }
//
//    private suspend fun getCurrentBook() = withContext(Dispatchers.IO) {
//        dao.getCurrentBook()
//    }
    fun fetchCurrent() {
        val currentBook = repository.currentBook
        try {
            _currentBook.postValue(currentBook!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

/**
 * Factory class to instantiate the [ReadviewViewModel] instance.
 */
class ReadviewViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReadviewViewModel(repository) as T
        }
        throw IllegalArgumentException("View model not inherited from [ReadviewViewModel]")
    }
}