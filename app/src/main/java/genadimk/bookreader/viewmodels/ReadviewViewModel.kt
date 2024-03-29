package genadimk.bookreader.viewmodels

import android.content.Context
import androidx.lifecycle.*
import genadimk.bookreader.model.Book
import genadimk.bookreader.model.BookRepository
import genadimk.bookreader.utils.asBook
import kotlinx.coroutines.*

class ReadviewViewModel(private val repository: BookRepository) : ViewModel() {

    val currentBook = repository.currentBook

    fun refreshCurrentBook(context: Context) = runBlocking {
        val result = repository.getCurrent()
        repository.updateCurrentBook(result.asBook(context), null)
    }

    fun setCurrentPage(book: Book) = viewModelScope.launch {
        repository.updateCurrentBook(book, null)
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