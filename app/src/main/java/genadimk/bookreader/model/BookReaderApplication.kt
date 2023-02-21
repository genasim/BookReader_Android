package genadimk.bookreader.model

import androidx.multidex.MultiDexApplication

class BookReaderApplication : MultiDexApplication() {

    val database: BookDatabase by lazy { BookDatabase.getDatabase(this) }

}