package uz.gita.exam5bookapp.presentation.ui.host_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.exam5bookapp.R
import uz.gita.exam5bookapp.databinding.HostScreenBinding
import uz.gita.exam5bookapp.presentation.ui.explore_screen.ExploreScreen
import uz.gita.exam5bookapp.presentation.ui.main_screen.MainScreen
import uz.gita.exam5bookapp.presentation.ui.profile_screen.ProfileScreen
import uz.gita.exam5bookapp.presentation.ui.saved_screen.SavedScreen


class HostScreen : Fragment(R.layout.host_screen) {
    private val binding by viewBinding(HostScreenBinding::bind)
    private val mainScreen by lazy{ MainScreen() }
    private val savedScreen by lazy{ SavedScreen() }
    private val exploreScreen by lazy{ ExploreScreen() }
    private val profileScreen by lazy{ ProfileScreen() }
    private lateinit var currentScreen: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addAllFragments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        addAllFragments()
        clicks()

    }

    private fun addAllFragments() {
        childFragmentManager.beginTransaction().add(R.id.container, mainScreen).commit()
        currentScreen = mainScreen
    }

    private fun clicks() {

        binding.bottomNav.setOnItemSelectedListener {
            val tr = childFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.home -> {
                    currentScreen = mainScreen
                    tr.replace(R.id.container, currentScreen).commit()
                }
                R.id.saved -> {
                    currentScreen = savedScreen
                    tr.replace(R.id.container, currentScreen).commit()
                }
                R.id.explore -> {
                    currentScreen = exploreScreen
                    tr.replace(R.id.container, currentScreen).commit()
                }
                R.id.account -> {
                    currentScreen = profileScreen
                    tr.replace(R.id.container, currentScreen).commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

}