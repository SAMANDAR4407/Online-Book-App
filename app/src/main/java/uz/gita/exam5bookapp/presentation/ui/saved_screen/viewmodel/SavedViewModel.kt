package uz.gita.exam5bookapp.presentation.ui.saved_screen.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.exam5bookapp.data.model.common.BookData

/**
 *    Created by Kamolov Samandar on 12.05.2023 at 16:29
 */

interface SavedViewModel {

    val savedLiveData: LiveData<List<BookData>>

}