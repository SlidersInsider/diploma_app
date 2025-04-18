package com.mzhadan.app.network.models.files

data class PrivateKeyRequest(
    val user_id: Int,
    val private_key: String
)