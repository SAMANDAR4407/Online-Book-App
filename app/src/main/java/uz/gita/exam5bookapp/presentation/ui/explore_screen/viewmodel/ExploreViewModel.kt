package uz.gita.exam5bookapp.presentation.ui.explore_screen.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.exam5bookapp.data.model.common.CategoryData

/**
 *    Author: Kamolov Samandar
 *    Created: 11.05.2023 at 12:29
 *    Project: Exam5BookApp
 */
interface ExploreViewModel {

    val liveData: LiveData<List<CategoryData>>

    fun getAllBooks()
}