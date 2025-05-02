package com.mzhadan.app.diploma.ui.pages.viewers.pdf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentPdfViewerBinding

class PdfViewerFragment : Fragment() {
    private lateinit var binding: FragmentPdfViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString("path")
        binding.filenameTV.text = path
    }
}