package uz.gita.exam5bookapp.data.source.local.pref

import android.content.Context
import uz.gita.exam5bookapp.app.App

/**
 *    Author Kamolov Samandar
 *    Created 09.05.2023 at 8:39
 *    Project Exam5BookApp
 */

class PrefHelper private constructor() {

    private val shared = App.instance.getSharedPreferences("BookApp", Context.MODE_PRIVATE)

    companion object {
        private lateinit var instance: PrefHelper

        fun getInstance(): PrefHelper {
            if (!(::instance.isInitialized))
                instance = PrefHelper()
            return instance
        }
    }

    var bookTitle: String?
        set(value) = shared.edit().putString("bookTitle", value).apply()
        get() = shared.getString("bookTitle", "")

    var bookAuthor: String?
        set(value) = shared.edit().putString("bookAuthor", value).apply()
        get() = shared.getString("bookAuthor", "")

    var about: String?
        set(value) = shared.edit().putString("about", value).apply()
        get() = shared.getString("about", "")

    var bookCover: String?
        set(value) = shared.edit().putString("bookCover", value).apply()
        get() = shared.getString("bookCover", "")

    var pageNumber: Int
        set(value) = shared.edit().putInt("pageNumber", value).apply()
        get() = shared.getInt("pageNumber",0)

    var totalPage: Int
        set(value) = shared.edit().putInt("totalPage", value).apply()
        get() = shared.getInt("totalPage",0)

}