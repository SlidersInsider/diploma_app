package com.mzhadan.app.network.models.files

data class DownloadFileResponse(
    val filename: String,
    val encrypted_key: String,
    val file_data: String
)
