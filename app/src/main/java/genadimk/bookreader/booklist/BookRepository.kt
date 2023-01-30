package genadimk.bookreader.booklist

import android.net.Uri
import com.pspdfkit.document.PdfDocument
import com.pspdfkit.document.PdfDocumentLoader
import genadimk.bookreader.ui.floatingButton.ButtonAdd

object BookRepository : Repository {
    private val data = mutableListOf(
        Book.Builder.name("11111").build(),
        Book.Builder.name("22222").build(),
        Book.Builder.name("33333").build(),
        Book.Builder.name("44444").build()
    )

    override var currentBook: Book = data[0]

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
        val uri = _uri ?: return Book.Builder.build()
        
        val document: PdfDocument =
            PdfDocumentLoader.openDocument(ButtonAdd.fragment.requireContext(), uri)

        val pages = document.pageCount
        val pagesContent: MutableMap<Int, String> = mutableMapOf()
        for (index in 0 until pages)
            pagesContent[index] = document.getPageText(index)

        return Book.Builder
            .name(document.title!!)
            .uri(uri)
            .content(pagesContent)
            .build()
    }
}