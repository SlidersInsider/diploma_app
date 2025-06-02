package com.mzhadan.app.diploma.ui.pages.project_info

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data ?: return@registerForActivityResult

            requireContext().contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )

            projectInfoViewModel.uploadFile(requireContext(), uri, pid, onSuccess = {
                Toast.makeText(requireContext(), "Файл успешно загружен!", Toast.LENGTH_SHORT).show()
                projectInfoViewModel.getFiles(pid, onError = { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                })
            }, onError = { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
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
            with(binding) {
                if (files.isNotEmpty()) {
                    filesRecyclerView.visibility = View.VISIBLE
                    tvNoFiles.visibility = View.GONE
                }

                projectInfoAdapter.setFiles(files)
                binding.filesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.filesRecyclerView.adapter = projectInfoAdapter
            }
        }

        if (projectInfoViewModel.getRoleId() < 3) {
            binding.fabAddFile.visibility = View.VISIBLE
        } else {
            binding.fabAddFile.visibility = View.GONE
        }

        arguments?.let {
            pid = it.getInt("project_id")
            projectInfoViewModel.getFiles(pid, onError = { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            })
        }

        binding.fabAddFile.setOnClickListener {
            val openDocumentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }
            openDocumentLauncher.launch(openDocumentIntent)
        }
    }

    private fun setupProjectsAdapter() {
        projectInfoAdapter = ProjectInfoAdapter(object: ProjectInfoAdapter.FilesViewHolder.Callback {
            override fun onFileDownloadClicked(file: FileResponse) {
                projectInfoViewModel.downloadFile(requireContext(), file.id, onSuccess = { path ->
                    when(getExtension(file.filename)) {
                        "txt" -> ScreenNavigator.openTxtViewerScreen(path, file.id, file.encryption_key)
                        "pdf" -> ScreenNavigator.openPdfViewerScreen(path)
                        "docx" -> ScreenNavigator.openWordViewerScreen(path)
                    }
                    Toast.makeText(requireContext(), "Файл успешно загружен: $path!", Toast.LENGTH_SHORT).show()
                }, onError = { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                })
            }
        })
    }

    private fun getExtension(filename: String) = filename.substringAfterLast(".".lowercase())
}