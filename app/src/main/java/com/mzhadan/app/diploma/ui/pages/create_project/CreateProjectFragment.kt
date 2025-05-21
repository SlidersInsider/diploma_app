package com.mzhadan.app.diploma.ui.pages.create_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentCreateProjectBinding
import com.mzhadan.app.diploma.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProjectFragment : Fragment() {
    private lateinit var binding: FragmentCreateProjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProjectBinding.inflate(inflater, container, false)
        return binding.root
    }
}