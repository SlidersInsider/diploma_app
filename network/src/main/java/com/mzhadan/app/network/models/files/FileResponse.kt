package com.mzhadan.app.network.models.files

data class FileResponse(
    val id: Int,
    val project_id: Int,
    val user_id: Int,
    val filename: String,
    val file_path: String,
    val encryption_key: String
)