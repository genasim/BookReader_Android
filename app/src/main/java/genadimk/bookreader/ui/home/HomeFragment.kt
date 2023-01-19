package genadimk.bookreader.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import genadimk.bookreader.booklist.BookListViewAdapter
import genadimk.bookreader.databinding.FragmentHomeBinding
import genadimk.bookreader.ui.floatingButton.AppFloatingButton
import genadimk.bookreader.ui.mainActivity.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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