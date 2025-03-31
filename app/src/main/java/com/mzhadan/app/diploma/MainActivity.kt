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
    }
}