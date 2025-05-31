package com.mzhadan.app.reader

import java.io.File
import java.io.IOException

object TxtWorker {

    @Throws(IOException::class)
    fun readFromFile(path: String): String {
        val file = File(path)
        if (!file.exists()) throw IOException("Файл не найден: $path")
        return file.inputStream().bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    fun writeToFile(path: String, content: String) {
        val file = File(path)
        file.outputStream().bufferedWriter().use { it.write(content) }
    }
}
