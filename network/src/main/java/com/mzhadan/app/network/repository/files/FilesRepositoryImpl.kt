package com.mzhadan.app.network.repository.files

import com.mzhadan.app.network.api.FilesApi
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val filesApi: FilesApi
) : FilesRepository {

}