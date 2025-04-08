package com.mzhadan.app.diploma.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentTransactionWrapper(
    private var fragmentManager: FragmentManager? = null,
    private var parentContainerId: Int = 0
) {
    fun addFragment(fragment: Fragment, tag: String = "UnknownFragment") {
        fragmentManager?.beginTransaction()?.apply {
            add(parentContainerId, fragment, tag)
            commit()
        }
    }

    fun replaceFragment(fragment: Fragment, tag: String = "UnknownFragment") {
        fragmentManager?.beginTransaction()?.apply {
            replace(parentContainerId, fragment, tag)
            addToBackStack(null)
            commit()
        }
    }

    fun removeFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            remove(fragment)
            commit()
        }
    }
}