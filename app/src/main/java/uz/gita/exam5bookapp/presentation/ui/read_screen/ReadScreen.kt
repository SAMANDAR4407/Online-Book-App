package uz.gita.exam5bookapp.presentation.ui.read_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.data.model.common.BookData
import uz.gita.exam5bookapp.data.source.local.pref.PrefHelper
import uz.gita.exam5bookapp.databinding.ReadScreenBinding
import java.io.File

/**
 *    Created by Kamolov Samandar on 12.05.2023 at 4:50
 */

class ReadScreen: Fragment(R.layout.read_screen), OnPageChangeListener,
    OnPageErrorListener {

    private val binding by viewBinding(ReadScreenBinding::bind)
    private val args: ReadScreenArgs by navArgs()
    private val shared = PrefHelper.getInstance()
    private var pageNumber = 0
    private lateinit var data: BookData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        data = args.data
        pageNumber = if (shared.bookTitle == data.title) shared.pageNumber else 0
        openBook(data)
    }

    private fun openBook(data: BookData) {
        binding.apply {
            pdfViewer.fromFile(File(data.reference))
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .enableDoubletap(true)
                .enableAnnotationRendering(false)
                .scrollHandle(DefaultScrollHandle(requireContext()))
                .onPageChange(this@ReadScreen)
                .onPageError(this@ReadScreen)
                .enableAntialiasing(true)
                .spacing(10)
                .nightMode(false)
                .pageFitPolicy(FitPolicy.BOTH)
                .load()
        }
    }

    override fun onPause() {
        super.onPause()
        if (pageNumber!=0) {
            shared.bookTitle = data.title
            shared.bookAuthor = data.author
            shared.bookCover = data.coverUrl
            shared.totalPage = binding.pdfViewer.pageCount
            shared.pageNumber = if (shared.totalPage-pageNumber == 1) pageNumber+1 else pageNumber
        } else if (pageNumber+1 == binding.pdfViewer.pageCount) {
            shared.bookTitle = ""
            shared.bookAuthor = ""
            shared.bookCover = ""
            shared.pageNumber = 0
            shared.totalPage = 0
        }
    }

    override fun onPageChanged(page: Int, pageCount: Int) { pageNumber = page }

    override fun onPageError(page: Int, t: Throwable?) {}
}