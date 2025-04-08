package com.mzhadan.app.diploma.ui.navigation

import android.view.View
import androidx.fragment.app.FragmentManager
import com.mzhadan.app.diploma.MainActivity
import com.mzhadan.app.diploma.ui.auth.login.LoginFragment
import com.mzhadan.app.diploma.ui.auth.registration.RegistrationFragment
import com.mzhadan.app.diploma.ui.pages.home.HomeFragment

object ScreenNavigator {
    private var fragmentNavigator: FragmentTransactionWrapper? = null

    private var mainActivity: MainActivity? = null
    private var loginScreenFragment: LoginFragment? = null
    private var registrationScreenFragment: RegistrationFragment? = null
    private var homeScreenFragment: HomeFragment? = null

    fun init(fragmentManager: FragmentManager, parentContainerId: Int, mainActivity: MainActivity) {
        this.fragmentNavigator = FragmentTransactionWrapper(fragmentManager, parentContainerId)
        this.mainActivity = mainActivity
    }

    fun openLoginScreen() {
        loginScreenFragment = LoginFragment()
        fragmentNavigator?.addFragment(loginScreenFragment!!, "LoginScreenFragment")
    }

    fun reopenLoginScreen() {
        registrationScreenFragment = null
        loginScreenFragment = LoginFragment()
        fragmentNavigator?.replaceFragment(loginScreenFragment!!, "LoginScreenFragment")
    }

    fun openRegistrationScreen() {
        registrationScreenFragment = RegistrationFragment()
        fragmentNavigator?.replaceFragment(registrationScreenFragment!!, "RegistrationScreenFragment")
    }

    fun openHomeScreen() {
        loginScreenFragment = null
        homeScreenFragment = HomeFragment()
        fragmentNavigator?.replaceFragment(homeScreenFragment!!, "HomeScreenFragment")

        if (!mainActivity?.isBottomNavViewVisible()!!) {
            mainActivity?.setBottomNavViewVisibility(View.VISIBLE)
        }
    }

    fun openProjectsScreen() {

    }

    fun openProfileScreen() {

    }
}