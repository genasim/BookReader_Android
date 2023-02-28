package genadimk.bookreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pdftron.pdf.PDFViewCtrl
import com.pdftron.pdf.utils.AppUtils
import genadimk.bookreader.R
import genadimk.bookreader.model.Book
import genadimk.bookreader.databinding.FragmentReadviewBinding
import genadimk.bookreader.BookReaderApplication
import genadimk.bookreader.view.floatingButton.AppFloatingButton
import genadimk.bookreader.viewmodels.ReadviewViewModel
import genadimk.bookreader.viewmodels.ReadviewViewModelFactory

class ReadviewFragment : Fragment() {

    private var _binding: FragmentReadviewBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ReadviewViewModel by activityViewModels {
        ReadviewViewModelFactory(
            (activity?.application as BookReaderApplication).repository
        )
    }

    lateinit var pdfViewCtrl: PDFViewCtrl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentReadviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AppFloatingButton.disable()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pdfViewCtrl = view.findViewById(R.id.pdfView)
        AppUtils.setupPDFViewCtrl(pdfViewCtrl)

        with(viewModel.currentBook) {
            if (this.value == null)
                viewModel.refreshCurrentBook()

            observe(viewLifecycleOwner) {
                renderPdf(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        viewModel.currentBook.value?.let {
            with(it) {
                page = pdfViewCtrl.currentPage
                viewModel.setCurrentPage(this)
            }
        }
    }

    private fun renderPdf(book: Book) {
        AppUtils.setupPDFViewCtrl(pdfViewCtrl)

        try {
            pdfViewCtrl.apply {
                openPDFUri(book.uri, null)
                currentPage = book.page
            }
        } catch (ex: Exception) {
            ex.printStackTrace();
        }

        (activity as MainActivity).supportActionBar?.title = book.name
    }


}