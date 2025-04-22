package com.mzhadan.app.diploma.ui.pages.viewers.txt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mzhadan.app.diploma.databinding.FragmentTxtViewerBinding

class TxtViewerFragment : Fragment() {
    private lateinit var binding: FragmentTxtViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTxtViewerBinding.inflate(inflater, container, false)
        return binding.root
    }
}