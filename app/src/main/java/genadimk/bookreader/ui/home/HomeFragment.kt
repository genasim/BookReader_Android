package genadimk.bookreader.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.booklist.BookRepository
import genadimk.bookreader.utils.NEW_BOOK_KEY
import genadimk.bookreader.databinding.FragmentHomeBinding
import genadimk.bookreader.observer.CallbackProxy
import genadimk.bookreader.observer.Observable
import genadimk.bookreader.observer.Observer
import genadimk.bookreader.ui.floatingButton.AppFloatingButton
import genadimk.bookreader.ui.floatingButton.ButtonAdd
import genadimk.bookreader.ui.mainActivity.MainViewModel

class HomeFragment : Fragment(), Observer {

    private var _binding: FragmentHomeBinding? = null

    /** This property is only valid between onCreateView and onDestroyView */
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    companion object : Observable by CallbackProxy()

    init {
        ButtonAdd.subscribe(this)
    }

    /** open file picker to choose pdf uri and send callback to [ButtonAdd] */
    override fun update(args: Observable.Arguments?) = getContent.launch("application/pdf")
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val newBook = BookRepository.createBookItem(requireContext(), uri)
            val args = Observable.Arguments(mapOf(NEW_BOOK_KEY to newBook))
            sendUpdateEvent(args)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.homeListView.adapter = BookListViewAdapter()

        AppFloatingButton.enable()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AppFloatingButton.apply { buttonHandler = buttonAdder }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}