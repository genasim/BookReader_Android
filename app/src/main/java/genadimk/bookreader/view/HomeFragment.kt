package genadimk.bookreader.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import genadimk.bookreader.MobileNavigationDirections
import genadimk.bookreader.databinding.FragmentHomeBinding
import genadimk.bookreader.model.BookListAdapter
import genadimk.bookreader.model.BookReaderApplication
import genadimk.bookreader.observer.CallbackProxy
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.utils.TAG
import genadimk.bookreader.view.floatingButton.AppFloatingButton
import genadimk.bookreader.viewmodels.HomeViewModel
import genadimk.bookreader.viewmodels.HomeViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    /** This property is only valid between onCreateView and onDestroyView */
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels {
        HomeViewModelFactory(
            (activity?.application as BookReaderApplication).database.getBookDao()
        )
    }

    companion object : Observable by CallbackProxy() {
        lateinit var contentPicker: ActivityResultLauncher<String>
        lateinit var permissionRequest: ActivityResultLauncher<String>
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            val newBook = BookRepository.createBookItem(uri)
//            val index = BookRepository.addItem(newBook)
//            binding.homeListView.adapter?.notifyItemInserted(index)
        }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.i(TAG, "Permission granted -> $it")
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        AppFloatingButton.enable()

        contentPicker = getContent
        permissionRequest = requestPermission

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = BookListAdapter(
            onItemClicked = {
                val action: NavDirections = MobileNavigationDirections.actionGlobalNavReadview()
                //  TODO: set current book to the one pressed
                findNavController().navigate(action)
            },
            onItemLongClicked = { book ->
                book.apply {
                    isChecked = !isChecked
                    card!!.isChecked = isChecked
                }
                when (book.isChecked) {
                    true  -> AppFloatingButton.apply { buttonHandler = buttonRemover }

                    false -> if (viewModel.noBooksAreChecked())
                        AppFloatingButton.apply { buttonHandler = buttonAdder }
                }

                true
            }
        )

        binding.homeListView.adapter = adapter
        viewModel.allBookEntriesLive.observe(viewLifecycleOwner) { bookEntries ->
            val newList = viewModel.updateBookList(bookEntries)
            adapter.submitList(newList)
            AppFloatingButton.apply { buttonHandler = buttonAdder }
        }

        // TODO: Remove button
        binding.tempButton.setOnClickListener { viewModel.addBook() }

        // TODO: Remove button
        binding.tempDelButton.setOnClickListener { showConfirmationBox() }

        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showConfirmationBox() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Dialog alert title")
            .setCancelable(true)
            .setMessage("Are you sure???")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.allBookEntriesLive.value?.let {
                    for (book in it) {
                        viewModel.delete(book)
                    }
                }
            }
            .setNeutralButton("Cancel") { _, _ -> }
            .show()
    }
}