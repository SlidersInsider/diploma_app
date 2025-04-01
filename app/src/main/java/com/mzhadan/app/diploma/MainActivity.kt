package com.mzhadan.app.diploma

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mzhadan.app.diploma.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllUsers()
        viewModel.getAllProjects()
        viewModel.getAllRoles()
        viewModel.getAllUsersFromProject(3)
        viewModel.getAllFiles()

        viewModel.users.observe(this) { users ->
            users.forEach {
                Log.d("User", "ID: ${it.id}, Name: ${it.username}, Role: ${it.role}")
            }
        }

        viewModel.projects.observe(this) { projects ->
            projects.forEach {
                Log.d("Project", "ID: ${it.id}, Name: ${it.name}, Description: ${it.description}")
            }
        }

        viewModel.roles.observe(this) { roles ->
            roles.forEach {
                Log.d("Role", "ID: ${it.id}, Name: ${it.name}")
            }
        }

        viewModel.ups.observe(this) { up ->
            Log.d("Project", "Name: ${up.project}")
            up.users.forEach {
                Log.d("User", "ID: ${it.id}, Name: ${it.username}")
            }
        }

        viewModel.files.observe(this) { files ->
            files.forEach {
                Log.d("File", "ID: ${it.id}, Name: ${it.filename}, Path: ${it.file_path}, ProjectId: ${it.project_id}")
            }
        }
    }
}