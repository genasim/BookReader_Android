package genadimk.bookreader.model

import androidx.multidex.MultiDexApplication

class BookReaderApplication : MultiDexApplication() {

    val repository: BookRepository = BookRepository(this)

}