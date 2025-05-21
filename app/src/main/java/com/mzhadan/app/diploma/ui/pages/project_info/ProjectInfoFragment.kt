package com.mzhadan.app.diploma.ui.pages.project_info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzhadan.app.diploma.TestKeys
import com.mzhadan.app.diploma.databinding.FragmentProjectInfoBinding
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import com.mzhadan.app.network.models.files.FileResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectInfoFragment : Fragment() {
    private lateinit var binding: FragmentProjectInfoBinding
    private val projectInfoViewModel: ProjectInfoViewModel by viewModels()

    private lateinit var projectInfoAdapter: ProjectInfoAdapter

    private var pid: Int = -1

    private val openDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            requireContext().contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            projectInfoViewModel.uploadFile(requireContext(), uri, 1, TestKeys.MANAGER_PUBLIC_KEY, onSuccess = {
                          projectInfoViewModel.getFiles(1)
            }, onError = {

            })
        }
    }

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
            pid = it.getInt("project_id")
            projectInfoViewModel.getFiles(pid)
        }

        binding.fabAddFile.setOnClickListener {
            openDocumentLauncher.launch(arrayOf("*/*"))
        }

    }

    private fun setupProjectsAdapter() {
        projectInfoAdapter = ProjectInfoAdapter(object: ProjectInfoAdapter.FilesViewHolder.Callback {
            override fun onFileDownloadClicked(file: FileResponse) {
                projectInfoViewModel.downloadFile(requireContext(), file.id, file.filename, TestKeys.WORKER_PRIVATE_KEY) { path ->
                    when(getExtension(file.filename)) {
                        "txt" -> ScreenNavigator.openTxtViewerScreen(path)
                        "pdf" -> ScreenNavigator.openPdfViewerScreen(path)
                        "docx" -> ScreenNavigator.openWordViewerScreen(path)
                    }
                }
            }
        })
    }

    private fun getExtension(filename: String) = filename.substringAfterLast(".".lowercase())
}