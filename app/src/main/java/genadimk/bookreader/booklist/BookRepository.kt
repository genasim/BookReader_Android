package genadimk.bookreader.booklist

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.io.File

object BookRepository: Repository {
    private val data = mutableListOf(
        Book.Builder().name("11111").build(),
        Book.Builder().name("22222").build(),
        Book.Builder().name("33333").build(),
        Book.Builder().name("44444").build()
    )

    override var currentBook: Book = data[0]

    override fun getRepository(): List<Book> = data.toList()

    override fun addItem(file: File): Int {
        val item = createBookItem(file)
        data.add(0, item)
        return data.indexOf(item)
    }

    private fun createBookItem(file: File): Book {
        file.readBytes()
        val pdfReader = PdfReader(file.readBytes())

        val pages = pdfReader.numberOfPages
        val pagesContent: MutableMap<Int, String> = mutableMapOf()
        for (index in 0..pages)
            pagesContent[index] = PdfTextExtractor.getTextFromPage(pdfReader, index)

        return Book.Builder()
            .name(file.nameWithoutExtension)
            .build()
    }

    override fun removeItem(item: Book): Int {
        val position = data.indexOf(item)
        data.remove(item)
        return position
    }
}