package genadimk.bookreader.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import genadimk.bookreader.BookReaderApplication
import genadimk.bookreader.R
import genadimk.bookreader.databinding.FragmentHomeBinding
import genadimk.bookreader.utils.asBookEntry
import genadimk.bookreader.utils.getFilename
import genadimk.bookreader.view.dialog.EditBoxDialog
import genadimk.bookreader.view.floatingButton.AppFloatingButton
import genadimk.bookreader.view.floatingButton.AppFloatingButton.Companion.buttonHandler
import genadimk.bookreader.viewmodels.HomeViewModel
import genadimk.bookreader.viewmodels.HomeViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    /** This property is only valid between onCreateView and onDestroyView */
    private val binding get() = _binding!!

    val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            (activity?.application as BookReaderApplication).repository
        )
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val filename = getFilename(requireActivity().contentResolver, uri)
                viewModel.addBook(uri, filename)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AppFloatingButton.enable(this, getContent)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BookListAdapter(
            onItemClicked = {
                val action: NavDirections = HomeFragmentDirections.actionNavHomeToNavReadview()
                viewModel.updateCurrentBook(it)
                findNavController().navigate(action)
            },
            onItemLongClicked = { book ->
                book.apply {
                    isChecked = !isChecked
                    card!!.isChecked = isChecked
                }
                when (book.isChecked) {
                    true -> AppFloatingButton.apply { buttonHandler = buttonRemover }
                    false -> if (viewModel.noBooksAreChecked())
                        AppFloatingButton.apply { buttonHandler = buttonAdder }
                }

                true
            },
            onEditClicked = { book ->
                EditBoxDialog {
                    val newBook = book.asBookEntry().copy(name = it?.text.toString())
                    viewModel.updateBook(newBook)
                }.show(parentFragmentManager, "Edit text")
            }
        )

        binding.homeListView.adapter = adapter
        viewModel.allBookEntriesLive.observe(viewLifecycleOwner) { bookEntries ->
            val newList = viewModel.updateBookList(bookEntries)
            adapter.submitList(newList)
            AppFloatingButton.apply { buttonHandler = buttonAdder }
        }

        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}