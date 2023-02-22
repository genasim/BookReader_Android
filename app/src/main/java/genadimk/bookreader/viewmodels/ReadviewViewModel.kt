package genadimk.bookreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import genadimk.bookreader.model.BookDao

class ReadviewViewModel(private val bookDao: BookDao) : ViewModel() {

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