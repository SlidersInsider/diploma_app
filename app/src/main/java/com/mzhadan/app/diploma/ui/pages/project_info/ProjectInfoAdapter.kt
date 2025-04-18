package com.mzhadan.app.diploma.ui.pages.project_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mzhadan.app.diploma.databinding.ItemFileBinding
import com.mzhadan.app.network.models.files.FileResponse

class ProjectInfoAdapter(
    private val callback: FilesViewHolder.Callback
): RecyclerView.Adapter<ProjectInfoAdapter.FilesViewHolder>() {

    private val filesList = ArrayList<FileResponse>()

    fun setFiles(newFilesList: List<FileResponse>) {
        filesList.clear()
        filesList.addAll(newFilesList)
    }

    class FilesViewHolder(
        private val binding: ItemFileBinding,
        private val callback: Callback
    ): RecyclerView.ViewHolder(binding.root) {

        interface Callback {
            fun onFileDownloadClicked(file: FileResponse)
        }

        fun bind(file: FileResponse) {
            with(binding) {
                projectNameTV.text = file.filename

                downloadButton.setOnClickListener {
                    callback.onFileDownloadClicked(file)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val filesListElementBinding = ItemFileBinding.inflate(layoutInflater, parent, false)
        return FilesViewHolder(filesListElementBinding, callback)
    }

    override fun getItemCount(): Int = filesList.size

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        holder.bind(filesList[position])
    }
}