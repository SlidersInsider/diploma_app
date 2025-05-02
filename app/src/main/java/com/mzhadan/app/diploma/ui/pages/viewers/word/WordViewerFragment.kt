package com.mzhadan.app.diploma.ui.pages.viewers.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentWordViewerBinding

class WordViewerFragment : Fragment() {
    private lateinit var binding: FragmentWordViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString("path")
        binding.filenameTV.text = path
    }
}