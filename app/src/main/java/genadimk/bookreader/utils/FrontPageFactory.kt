package genadimk.bookreader.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.pdftron.pdf.PDFDoc
import com.pdftron.pdf.PDFDraw
import com.pdftron.pdf.PDFNet
import java.io.InputStream

class FrontPageFactory(private val context: Context) : PDFNet() {
    private val draw = PDFDraw()

    fun produceBitmap(filepath: String): Bitmap {
        val inputStream = getInputStreamByUri(context, Uri.parse(filepath))
        val doc = PDFDoc(inputStream)
        draw.setDPI(100.0)

        val page = doc.getPage(1)
        inputStream?.close()
        
        return draw.getBitmap(page)
    }

    private fun getInputStreamByUri(context: Context, uri: Uri?): InputStream? {
        return try {
            context.contentResolver.openInputStream(uri!!)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
