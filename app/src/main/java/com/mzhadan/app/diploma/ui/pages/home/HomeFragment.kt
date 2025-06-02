package com.mzhadan.app.diploma.ui.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mzhadan.app.diploma.MainViewModel
import com.mzhadan.app.diploma.databinding.FragmentHomeBinding
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import com.mzhadan.app.network.models.projects.ProjectResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.updateBottomNavigationBarVisibility(View.VISIBLE)
        if (!homeViewModel.isUserUidedIn()) {
            homeViewModel.getUserByName(onError = { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            })
        }

        setupProjectsAdapter()
        homeViewModel.projects.observe(viewLifecycleOwner) { projects ->
            with(binding) {
                if (projects.isNotEmpty()) {
                    rvProjects.visibility = View.VISIBLE
                    tvNoProjects.visibility = View.GONE
                }
                homeAdapter.setProjects(projects)
                rvProjects.layoutManager = LinearLayoutManager(requireContext())
                rvProjects.adapter = homeAdapter
            }
        }

        homeViewModel.getAllProjects(onError = { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupProjectsAdapter() {
        homeAdapter = HomeAdapter(object: HomeAdapter.ProjectsViewHolder.Callback {
            override fun onProjectClicked(project: ProjectResponse) {
                ScreenNavigator.openProjectInfoScreen(project.id)
            }
        })
    }
}