package com.mzhadan.app.diploma.ui.pages.project_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzhadan.app.diploma.TestKeys
import com.mzhadan.app.diploma.databinding.FragmentProjectInfoBinding
import com.mzhadan.app.network.models.files.FileResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectInfoFragment : Fragment() {
    private lateinit var binding: FragmentProjectInfoBinding
    private val projectInfoViewModel: ProjectInfoViewModel by viewModels()

    private lateinit var projectInfoAdapter: ProjectInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupProjectsAdapter()
        projectInfoViewModel.files.observe(viewLifecycleOwner) { files ->
            projectInfoAdapter.setFiles(files)
            binding.filesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.filesRecyclerView.adapter = projectInfoAdapter
        }

        arguments?.let {
            projectInfoViewModel.getFiles(it.getInt("project_id"))
        }

    }

    private fun setupProjectsAdapter() {
        projectInfoAdapter = ProjectInfoAdapter(object: ProjectInfoAdapter.FilesViewHolder.Callback {
            override fun onFileDownloadClicked(file: FileResponse) {
                projectInfoViewModel.downloadFile(requireContext(), file.id, file.filename, TestKeys.WORKER_PRIVATE_KEY)
            }
        })
    }
}