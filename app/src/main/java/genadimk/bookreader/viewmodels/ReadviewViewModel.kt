package genadimk.bookreader.viewmodels

import androidx.lifecycle.*
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.model.BookEntry
import genadimk.bookreader.model.BookRepository
import kotlinx.coroutines.*

class ReadviewViewModel(private val repository: BookRepository) : ViewModel() {

    val currentBook = repository.currentBook

    fun refreshCurrentBook() = viewModelScope.launch {
//        refreshFromDatabase()
    }

//    suspend fun refreshFromDatabase() = withContext(Dispatchers.IO) {
//        val result = async { repository.database.bookDao.getCurrentBook() }.await()
//        _currentBook.postValue(result)
//    }

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