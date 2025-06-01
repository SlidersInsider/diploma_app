package com.mzhadan.app.diploma.ui.pages.create_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mzhadan.app.diploma.databinding.FragmentCreateProjectBinding
import com.mzhadan.app.diploma.databinding.FragmentProfileBinding
import com.mzhadan.app.diploma.ui.auth.AuthViewModel
import com.mzhadan.app.diploma.ui.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProjectFragment : Fragment() {
    private lateinit var binding: FragmentCreateProjectBinding
    private val createProjectViewModel: CreateProjectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etProjectName.addTextChangedListener { checkFields() }
            etProjectDescription.addTextChangedListener { checkFields() }

            btnCreateProject.setOnClickListener {
                createProjectViewModel.createProject(etProjectName.text.toString(), etProjectDescription.text.toString(), onSuccess = {
                    Toast.makeText(requireContext(), "Новый проект успешно создан", Toast.LENGTH_SHORT).show()
                    ScreenNavigator.openProjectsScreen()
                }, onError = { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                } )
            }
        }
    }

    private fun checkFields() {
        with(binding) {
            val nameNotEmpty = etProjectName.text?.isNotBlank() == true
            val descNotEmpty = etProjectDescription.text?.isNotBlank() == true
            btnCreateProject.isEnabled = nameNotEmpty && descNotEmpty
        }
    }
}