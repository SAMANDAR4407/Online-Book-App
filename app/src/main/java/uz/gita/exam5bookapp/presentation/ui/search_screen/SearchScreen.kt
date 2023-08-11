package uz.gita.exam5bookapp.presentation.ui.search_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.databinding.SearchScreenBinding
import uz.gita.exam5bookapp.presentation.adapters.BookListAdapter
import uz.gita.exam5bookapp.presentation.ui.search_screen.viewmodel.SearchViewModelImpl

/**
 *    Created by Kamolov Samandar on 05.07.2023 at 15:04
 */

class SearchScreen: Fragment(R.layout.search_screen) {
    private val binding by viewBinding(SearchScreenBinding::bind)
    private val viewModel by viewModels<SearchViewModelImpl>()
    private val adapter = BookListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clicks()
        binding.placeholder.visibility = View.VISIBLE
        binding.apply {

            recyclerView.adapter = adapter

            search.doAfterTextChanged {
                val text = it?.trim().toString()
                viewModel.searchBooks(text)
            }

            viewModel.searchResults.observe(viewLifecycleOwner){
                Log.d("TTT", "onViewCreated: ${it.size}")
                if (it.isEmpty()){
                    placeholder.visibility = View.VISIBLE
                } else {
                    placeholder.visibility = View.GONE
                    adapter.submitList(it)
                }
            }

        }
    }

    private fun clicks() {
        adapter.putReadListener {
            val bundle = bundleOf("book" to arrayListOf(it.id.toString(), it.title, it.author, it.genre, it.about, it.page.toString(), it.rate.toString(), it.year, it.bookUrl, it.coverUrl, it.reference, it.lastPage.toString(), it.saved.toString()))
            findNavController().navigate(R.id.descriptionScreen, bundle)
        }
    }

}