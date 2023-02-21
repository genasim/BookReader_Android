package genadimk.bookreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import genadimk.bookreader.model.BookDao

class HomeViewModel(private val bookDao: BookDao) : ViewModel() {
    
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