package genadimk.bookreader.ui.readview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pspdfkit.configuration.PdfConfiguration
import com.pspdfkit.ui.PdfFragment
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

        val config = PdfConfiguration.Builder()
            .restoreLastViewedPage(true)
            .build()

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.pdfFragment_container,
                PdfFragment.newInstance(BookRepository.currentBook.uri, config))
            .commit()

        (activity as MainActivity).supportActionBar?.title = BookRepository.currentBook.name

        AppFloatingButton.disable()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}