package genadimk.bookreader.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pdftron.pdf.utils.AppUtils
import genadimk.bookreader.BookReaderApplication
import genadimk.bookreader.databinding.FragmentReadviewBinding
import genadimk.bookreader.model.Book
import genadimk.bookreader.view.dialog.NoCurrentBookDialog
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentReadviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AppFloatingButton.disable()

        AppUtils.setupPDFViewCtrl(binding.pdfView)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            with(viewModel.currentBook) {
                if (this.value == null)
                    viewModel.refreshCurrentBook(requireContext())

                observe(viewLifecycleOwner) {
                    renderPdf(it!!)
                }
            }
        } catch (ex: Exception) {
            NoCurrentBookDialog {
                val action = ReadviewFragmentDirections.actionNavReadviewToNavHome()
                findNavController().navigate(action)
            }.show(parentFragmentManager, "No current Book Alert")
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
                page = binding.pdfView.currentPage
                viewModel.setCurrentPage(this)
            }
        }
    }

    private fun renderPdf(book: Book) {
        try {
            binding.pdfView.apply {
                openPDFUri(book.uri, null)
                currentPage = book.page
            }

            binding.thumbnailSlider.visibility = when (binding.pdfView.pageCount) {
                1 -> View.GONE
                else -> View.VISIBLE
            }
            binding.thumbnailSlider.refreshPageCount()

        } catch (ex: Exception) {
            ex.printStackTrace();
        }

        (activity as MainActivity).supportActionBar?.title = book.name
    }
}