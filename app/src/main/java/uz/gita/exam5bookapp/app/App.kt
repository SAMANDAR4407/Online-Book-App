package uz.gita.exam5bookapp.app

import android.app.Application
import uz.gita.exam5bookapp.data.source.local.room.BookDatabase
import uz.gita.exam5bookapp.domain.repository.impl.BookRepositoryImpl
import uz.gita.exam5bookapp.domain.usecase.main_screen.impl.GetAllBooksUseCaseImpl
import uz.gita.exam5bookapp.domain.usecase.saved_screen.impl.SavedBooksUseCaseImpl

/**
 *    Author Kamolov Samandar
 *    Created 09.05.2023 at 6:07
 *    Project Exam5BookApp
 */

class App : Application(){
    companion object{
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        BookDatabase.init(this)
        BookRepositoryImpl.init(this)
        GetAllBooksUseCaseImpl.init()
        SavedBooksUseCaseImpl.init()
        instance = this
    }
}