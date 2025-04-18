package com.mzhadan.app.diploma.ui.pages.projects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mzhadan.app.diploma.databinding.ItemProjectBinding
import com.mzhadan.app.network.models.projects.ProjectResponse

class ProjectsAdapter(
    private val callback: ProjectsViewHolder.Callback
): RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    private val projectsList = ArrayList<ProjectResponse>()

    fun setProjects(newProjectsList: List<ProjectResponse>) {
        projectsList.clear()
        projectsList.addAll(newProjectsList)
    }

    class ProjectsViewHolder(
        private val binding: ItemProjectBinding,
        private val callback: Callback
    ): RecyclerView.ViewHolder(binding.root) {

        interface Callback {
            fun onJoinClicked(project: ProjectResponse)
        }

        fun bind(project: ProjectResponse) {
            with(binding) {
                projectNameTV.text = project.name
                projectDescriptionTV.text = project.description

                joinButton.setOnClickListener {
                    callback.onJoinClicked(project)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val projectsListElementBinding = ItemProjectBinding.inflate(layoutInflater, parent, false)
        return ProjectsViewHolder(projectsListElementBinding, callback)
    }

    override fun getItemCount(): Int = projectsList.size

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.bind(projectsList[position])
    }
}