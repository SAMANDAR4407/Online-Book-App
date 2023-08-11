package uz.gita.exam5bookapp.presentation.ui.main_screen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.gita.exam5bookapp.BuildConfig
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.source.local.pref.PrefHelper
import uz.gita.exam5bookapp.databinding.MainScreenBinding
import uz.gita.exam5bookapp.presentation.adapters.BookListMainAdapter
import uz.gita.exam5bookapp.presentation.ui.main_screen.viewmodel.MainViewModel
import uz.gita.exam5bookapp.presentation.ui.main_screen.viewmodel.MainViewModelImpl
import java.io.File

class MainScreen : Fragment(R.layout.main_screen) {
    private val binding by viewBinding(MainScreenBinding::bind)
    private val shared = PrefHelper.getInstance()
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val path = "/data/user/0/uz.gita.exam5bookapp/files/books/"

    private val adapter = BookListMainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        liveData()
        setAdapter()
        clicks()

        if (shared.totalPage != shared.pageNumber && File("$path${shared.bookTitle!!.replace(" ","_")}.pdf").exists()){
            binding.apply {
                    recentBook.visibility = View.VISIBLE
                    bookTitle.text = shared.bookTitle
                    bookTitle.maxLines = 1
                    bookmark.scaleType = ImageView.ScaleType.CENTER_CROP
                    bookTitle.maxWidth = 400
                    bookAuthor.text = shared.bookAuthor
                    bookmark.setPadding(7,7,7,7)
                    Glide.with(bookmark).load(shared.bookCover).into(bookmark)
            }
        } else {
            shared.bookTitle = ""
            shared.bookAuthor = ""
            shared.pageNumber = 0
            shared.totalPage = 0
            shared.bookCover = ""
            binding.recentBook.visibility = View.GONE
        }

    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun liveData() {
        viewModel.liveData.observe(viewLifecycleOwner, classicBooksObserver)
    }

    private fun clicks() {
        adapter.putReadListener{
            val bundle = bundleOf("book" to arrayListOf(it.id.toString(),it.title,it.author,it.genre,it.about,it.page.toString(),it.rate.toString(),it.year,it.bookUrl,it.coverUrl,it.reference,it.lastPage.toString(),it.saved.toString()))
            findNavController().navigate(R.id.descriptionScreen, bundle)
        }

        binding.share.setOnClickListener { shareApp() }

        binding.recentBook.setOnClickListener {
            val bundle = bundleOf("book" to arrayListOf("0",shared.bookTitle,shared.bookAuthor,"genre",shared.about,shared.totalPage.toString(),"3","2002","book",shared.bookCover,"/data/user/0/uz.gita.exam5bookapp/files/books/${shared.bookTitle!!.replace(" ","_")}.pdf","100","false"))
            findNavController().navigate(R.id.descriptionScreen, bundle)
        }
    }

    private val classicBooksObserver = Observer<List<BookData>>{
        adapter.submitList(it)
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            var shareMessage = "Book app.".trim() + "\n"
            shareMessage = "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: java.lang.Exception) {
            e.toString()
        }
    }
}