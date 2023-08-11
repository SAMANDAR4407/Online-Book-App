package uz.gita.exam5bookapp.presentation.ui.explore_screen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.data.model.common.CategoryData
import uz.gita.exam5bookapp.databinding.ExploreScreenBinding
import uz.gita.exam5bookapp.presentation.adapters.BookCategoryAdapter
import uz.gita.exam5bookapp.presentation.ui.explore_screen.viewmodel.ExploreViewModelImpl

class ExploreScreen : Fragment(R.layout.explore_screen) {
    private val binding by viewBinding(ExploreScreenBinding::bind)
    private val viewModel : ExploreViewModelImpl by viewModels()

    private val adapter = BookCategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        clicks()
        setAdapter()
        liveData()
    }

    private fun liveData() {
        viewModel.liveData.observe(viewLifecycleOwner,observer)
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.setChildItemListener {
            val bundle = bundleOf("book" to arrayListOf(it.id.toString(),it.title,it.author,it.genre,it.about,it.page.toString(),it.rate.toString(),it.year,it.bookUrl,it.coverUrl,it.reference,it.lastPage.toString(),it.saved.toString()))
            findNavController().navigate(R.id.descriptionScreen, bundle)
        }
    }

    private fun clicks() {
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchScreen, null)
        }
    }

    private val observer = Observer<List<CategoryData>>{
        adapter.submitList(it)
    }
}