package genadimk.bookreader.booklist

import android.content.Context
import android.net.Uri
import com.pspdfkit.document.PdfDocument
import com.pspdfkit.document.PdfDocumentLoader
import java.io.File

object BookRepository : Repository {
    private val data = mutableListOf(
        Book.Builder.name("11111").build(),
        Book.Builder.name("22222").build(),
        Book.Builder.name("33333").build(),
        Book.Builder.name("44444").build()
    )

    override var currentBook: Book = data[0]

    override fun getRepository(): List<Book> = data.toList()

    override fun addItem(uri: Uri): Int {
        val item = createBookItem(uri)
        data.add(0, item)
        return data.indexOf(item)
    }

    private fun createBookItem(uri: Uri): Book {
        val file = File(uri.toString())
//        val document: PdfDocument = PdfDocumentLoader.openDocument(Context, uri)

//        val pages = pdfReader.numberOfPages
//        val pagesContent: MutableMap<Int, String> = mutableMapOf()
//        for (index in 0..pages)
//            pagesContent[index] = PdfTextExtractor.getTextFromPage(pdfReader, index)

        return Book.Builder
            .name(file.nameWithoutExtension)
            .uri(uri)
            .build()
    }

    override fun removeItem(item: Book): Int {
        val position = data.indexOf(item)
        data.remove(item)
        return position
    }
}