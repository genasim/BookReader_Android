package genadimk.bookreader.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val mutableBookName = MutableLiveData<String>()
    val currentBookName: LiveData<String>
        get() = mutableBookName

    fun selectBook(item: String) {
        mutableBookName.value = item
    }
}