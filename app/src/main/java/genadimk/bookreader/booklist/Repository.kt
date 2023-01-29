package genadimk.bookreader.booklist

import android.net.Uri

interface Repository {
    var currentBook: Book

    fun getRepository(): List<Book>

    /** Adds an item to the list and returns it's index */
    fun addItem(uri: Uri): Int

    /** Removers an item from the list and returns it's index */
    fun removeItem(item: Book): Int
}