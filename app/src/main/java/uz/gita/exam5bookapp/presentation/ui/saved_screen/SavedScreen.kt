package uz.gita.exam5bookapp.presentation.ui.saved_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.databinding.MainScreenBinding
import uz.gita.exam5bookapp.databinding.SavedScreenBinding
import uz.gita.exam5bookapp.presentation.adapters.BookListAdapter
import uz.gita.exam5bookapp.presentation.ui.saved_screen.viewmodel.SaveViewModelImpl

class SavedScreen : Fragment(R.layout.saved_screen) {
    private val binding by viewBinding(SavedScreenBinding::bind)
    private val adapter = BookListAdapter()
    private val viewModel by viewModels<SaveViewModelImpl>()
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.savedLiveData.observe(viewLifecycleOwner) {
            Log.d("AAA", "onViewCreated: ${it.size} ")
            adapter.submitList(it)
            binding.swiper.isRefreshing = false
        }

        setAdapter()
        clicks()

        binding.swiper.setOnRefreshListener {
            viewModel.loadBooks()
        }
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun clicks() {
        adapter.putReadListener {
            val bundle = bundleOf("book" to arrayListOf(it.id.toString(), it.title, it.author, it.genre, it.page.toString(), it.rate.toString(), it.year, it.bookUrl, it.coverUrl, it.reference, it.lastPage.toString(), it.saved.toString()))
            findNavController().navigate(R.id.descriptionScreen, bundle)
        }
    }
}