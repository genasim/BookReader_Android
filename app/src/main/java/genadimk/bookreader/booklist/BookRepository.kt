package genadimk.bookreader.booklist

import android.content.Context
import android.net.Uri
import com.pspdfkit.document.PdfDocumentLoader

object BookRepository : Repository {
    private val data = mutableListOf<Book>()

    lateinit var currentBook: Book

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

    fun createBookItem(context: Context, _uri: Uri?): Book {
        val uri = _uri ?: return Book.Builder().build()

        val document = PdfDocumentLoader.openDocumentAsync(context, uri).blockingGet()

        val pages = document.pageCount
        val pagesContent: MutableMap<Int, String> = mutableMapOf()
        for (index in 0 until pages)
            pagesContent[index] = document.getPageText(index)

        return Book.Builder()
            .name(document.title!!)
            .uri(uri)
            .content(pagesContent)
            .build()
    }
}