package genadimk.bookreader.ui.readview

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pdftron.pdf.config.ViewerBuilder2
import com.pdftron.pdf.controls.PdfViewCtrlTabHostFragment2
import genadimk.bookreader.R
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.databinding.FragmentReadviewBinding
import genadimk.bookreader.ui.floatingButton.AppFloatingButton
import genadimk.bookreader.ui.mainActivity.MainActivity

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
//        val pdfViewCtrl: PDFViewCtrl = view.findViewById(R.id.pdfView);
//        try {
//            pdfViewCtrl.openPDFUri(Uri.parse("https://pdftron.s3.amazonaws.com/downloads/pdfref.pdf"),
//                null);
//        } catch (ex: Exception) {
//            ex.printStackTrace();
//        }
        BookRepository.currentBook?.let {
            addViewerFragment(R.id.pdfView, requireContext(), it.uri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** Add a viewer fragment to the layout container in the specified
     *  activity, and returns the added fragment */
    private fun addViewerFragment(
        @IdRes fragmentContainer: Int,
        context: Context,
        fileUri: Uri,
    ): PdfViewCtrlTabHostFragment2 {

        // Create the viewer fragment
        val fragment = ViewerBuilder2.withUri(fileUri, null).build(context)

        // Add the fragment to the layout fragment container
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(fragmentContainer, fragment)
            .commit()

        (activity as MainActivity).supportActionBar?.title = BookRepository.currentBook!!.name

        return fragment
    }
}