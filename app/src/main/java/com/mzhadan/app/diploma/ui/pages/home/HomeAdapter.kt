package com.mzhadan.app.diploma.ui.pages.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mzhadan.app.diploma.databinding.ItemHomeBinding
import com.mzhadan.app.network.models.projects.ProjectResponse

class HomeAdapter(
    private val callback: ProjectsViewHolder.Callback
): RecyclerView.Adapter<HomeAdapter.ProjectsViewHolder>() {

    private val projectsList = ArrayList<ProjectResponse>()

    fun setProjects(newProjectsList: List<ProjectResponse>) {
        projectsList.clear()
        projectsList.addAll(newProjectsList)
    }

    class ProjectsViewHolder(
        private val binding: ItemHomeBinding,
        private val callback: Callback
    ): RecyclerView.ViewHolder(binding.root) {

        interface Callback {
            fun onProjectClicked(project: ProjectResponse)
        }

        fun bind(project: ProjectResponse) {
            with(binding) {
                projectNameTV.text = project.name
                projectDescriptionTV.text = project.description

                projectCV.setOnClickListener {
                    callback.onProjectClicked(project)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val projectsListElementBinding = ItemHomeBinding.inflate(layoutInflater, parent, false)
        return ProjectsViewHolder(projectsListElementBinding, callback)
    }

    override fun getItemCount(): Int = projectsList.size

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.bind(projectsList[position])
    }
}