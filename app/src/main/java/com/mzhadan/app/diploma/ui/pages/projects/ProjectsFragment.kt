package com.mzhadan.app.diploma.ui.pages.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzhadan.app.diploma.databinding.FragmentProjectsBinding
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import com.mzhadan.app.network.models.projects.ProjectResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectsFragment : Fragment() {
    private lateinit var binding: FragmentProjectsBinding
    private val projectsViewModel: ProjectsViewModel by viewModels()

    private lateinit var projectsAdapter: ProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupProjectsAdapter()
        projectsViewModel.projects.observe(viewLifecycleOwner) { projects ->
            projectsAdapter.setProjects(projects)
            binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.projectsRecyclerView.adapter = projectsAdapter
        }

        projectsViewModel.getAllProjects()

        binding.fabAddProject.setOnClickListener {
            ScreenNavigator.openCreateProjectFragment()
        }
    }

    private fun setupProjectsAdapter() {
        projectsAdapter = ProjectsAdapter(object: ProjectsAdapter.ProjectsViewHolder.Callback {
            override fun onJoinClicked(project: ProjectResponse) {
                projectsViewModel.joinProject(project.id) {
//                    Toast.makeText(requireContext(), "Запрос отправлен!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}