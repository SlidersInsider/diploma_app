package com.mzhadan.app.diploma.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.mzhadan.app.diploma.MainActivity
import com.mzhadan.app.diploma.ui.auth.login.LoginFragment
import com.mzhadan.app.diploma.ui.auth.registration.RegistrationFragment
import com.mzhadan.app.diploma.ui.pages.create_project.CreateProjectFragment
import com.mzhadan.app.diploma.ui.pages.home.HomeFragment
import com.mzhadan.app.diploma.ui.pages.profile.ProfileFragment
import com.mzhadan.app.diploma.ui.pages.project_info.ProjectInfoFragment
import com.mzhadan.app.diploma.ui.pages.projects.ProjectsFragment
import com.mzhadan.app.diploma.ui.pages.viewers.pdf.PdfViewerFragment
import com.mzhadan.app.diploma.ui.pages.viewers.txt.TxtViewerFragment
import com.mzhadan.app.diploma.ui.pages.viewers.word.WordViewerFragment

object ScreenNavigator {
    private var fragmentNavigator: FragmentTransactionWrapper? = null

    private var mainActivity: MainActivity? = null
    private var loginScreenFragment: LoginFragment? = null
    private var registrationScreenFragment: RegistrationFragment? = null
    private var homeScreenFragment: HomeFragment? = null
    private var projectsScreenFragment: ProjectsFragment? = null
    private var profileScreenFragment: ProfileFragment? = null
    private var projectInfoFragment: ProjectInfoFragment? = null
    private var txtViewerFragment: TxtViewerFragment? = null
    private var pdfViewerFragment: PdfViewerFragment? = null
    private var wordViewerFragment: WordViewerFragment? = null
    private var createProjectFragment: CreateProjectFragment? = null

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

    fun openProjectInfoScreen(projectId: Int) {
        projectInfoFragment = ProjectInfoFragment().apply {
            val bundle = Bundle()
            bundle.putInt("project_id", projectId)
            arguments = bundle
        }
        fragmentNavigator?.replaceFragment(projectInfoFragment!!, "ProjectInfoFragment")
    }

    fun openProjectsScreen() {
        projectsScreenFragment = ProjectsFragment()
        fragmentNavigator?.replaceFragment(projectsScreenFragment!!, "ProjectsScreenFragment")
    }

    fun openProfileScreen() {
        profileScreenFragment = ProfileFragment()
        fragmentNavigator?.replaceFragment(profileScreenFragment!!, "ProfileScreenFragment")
    }

    fun openCreateProjectFragment() {
        createProjectFragment = CreateProjectFragment()
        fragmentNavigator?.replaceFragment(createProjectFragment!!, "CreateProjectFragment")
    }

    fun openTxtViewerScreen(filePath: String) {
        txtViewerFragment = TxtViewerFragment().apply {
            val bundle = Bundle()
            bundle.putString("path", filePath)
            arguments = bundle
        }
        fragmentNavigator?.replaceFragment(txtViewerFragment!!, "TxtViewerFragment")
    }

    fun openPdfViewerScreen(filePath: String) {
        pdfViewerFragment = PdfViewerFragment().apply {
            val bundle = Bundle()
            bundle.putString("path", filePath)
            arguments = bundle
        }
        fragmentNavigator?.replaceFragment(pdfViewerFragment!!, "PdfViewerFragment")
    }

    fun openWordViewerScreen(filePath: String) {
        wordViewerFragment = WordViewerFragment().apply {
            val bundle = Bundle()
            bundle.putString("path", filePath)
            arguments = bundle
        }
        fragmentNavigator?.replaceFragment(wordViewerFragment!!, "WordViewerFragment")
    }
}