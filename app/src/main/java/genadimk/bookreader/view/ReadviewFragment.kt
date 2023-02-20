package genadimk.bookreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pdftron.pdf.PDFViewCtrl
import genadimk.bookreader.R
import genadimk.bookreader.booklist.Book
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.databinding.FragmentReadviewBinding
import genadimk.bookreader.view.floatingButton.AppFloatingButton
import genadimk.bookreader.viewmodels.ReadviewViewModel

class ReadviewFragment : Fragment() {

    private var _binding: FragmentReadviewBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val readviewViewModel =
            ViewModelProvider(this)[ReadviewViewModel::class.java]

        _binding = FragmentReadviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textReadview
//        readviewViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        AppFloatingButton.disable()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        BookRepository.currentBook?.let { book ->
            renderPdf(view, book)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        view?.let {
            val pdfViewCtrl: PDFViewCtrl = it.findViewById(R.id.pdfView);
            BookRepository.currentBook?.page = pdfViewCtrl.currentPage
        }
    }

    private fun renderPdf(view: View, book: Book) {
        val pdfViewCtrl: PDFViewCtrl = view.findViewById(R.id.pdfView)
        try {
            pdfViewCtrl.apply {
                BookRepository.currentBook?.let { book ->
                    openPDFUri(book.uri, null)
                    currentPage = book.page
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace();
        }

        (activity as MainActivity).supportActionBar?.title = book.name
    }
}