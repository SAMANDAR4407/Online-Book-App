package uz.gita.exam5bookapp.presentation.ui.description_screen.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.exam5bookapp.data.model.common.BookData

/**
 *    Created by Kamolov Samandar on 14.05.2023 at 5:19
 */

interface DescriptionViewModel {

    val fileDownloadedLiveData: LiveData<BookData>
    val errorDownloadLiveData: LiveData<String>

    fun downloadBook(context: Context, data: BookData)
}