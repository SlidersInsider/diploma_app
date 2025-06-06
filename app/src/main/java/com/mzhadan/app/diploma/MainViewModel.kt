package com.mzhadan.app.diploma

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _bottomNavigationBarVisible = MutableLiveData<Boolean>()
    val bottomNavigationBarVisible: LiveData<Boolean> get() = _bottomNavigationBarVisible

    fun updateBottomNavigationBarVisibility(visibility: Int) {
        when (visibility) {
            View.VISIBLE -> {
                _bottomNavigationBarVisible.postValue(true)
            }
            View.GONE -> {
                _bottomNavigationBarVisible.postValue(false)
            }
        }
    }
}
