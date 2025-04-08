package com.mzhadan.app.diploma

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mzhadan.app.diploma.databinding.ActivityMainBinding
import com.mzhadan.app.diploma.ui.auth.AuthViewModel
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragmenting()
        setFragmentRouting()

        mainViewModel.bottomNavigationBarVisible.observe(this) { isVisible ->
            if (isVisible) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    private fun initFragmenting() {
        ScreenNavigator.init(supportFragmentManager, R.id.fragmentContainer, this)
        if (authViewModel.isUserLoggedIn()) {
            ScreenNavigator.openHomeScreen()
        } else {
            ScreenNavigator.openLoginScreen()
        }
    }

    private fun setFragmentRouting() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    ScreenNavigator.openHomeScreen()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_projects -> {
                    ScreenNavigator.openProjectsScreen()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    ScreenNavigator.openProfileScreen()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    fun setBottomNavViewVisibility(visibility: Int) {
        binding.bottomNavigationView.visibility = visibility
    }

    fun isBottomNavViewVisible(): Boolean {
        return binding.bottomNavigationView.isVisible
    }
}