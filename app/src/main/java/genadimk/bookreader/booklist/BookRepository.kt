package genadimk.bookreader.booklist

import android.net.Uri

object BookRepository : Repository {
    private val data = mutableListOf<Book>()

    var currentBook: Book? = null

    override fun getRepository(): List<Book> = data.toList()

    override fun addItem(item: Book): Int {
        data.add(0, item)
        return data.indexOf(item)
    }

    override fun removeItem(item: Book): Int {
        val position = data.indexOf(item)
        data.remove(item)
        return position
    }

    fun createBookItem(_uri: Uri?): Book {
        val uri = _uri ?: return Book.Builder().build()

        return Book.Builder()
            .uri(uri)
            .build()
    }
}