package com.mzhadan.app.diploma.crypto

import java.io.File
import java.io.InputStream
import javax.crypto.SecretKey

object FileCryptoUtils {
    fun encryptFile(file: InputStream, aesKey: SecretKey): Pair<ByteArray, ByteArray> {
        val data = file.readBytes()
        return AesUtils.encrypt(data, aesKey)
    }

    fun decryptFile(outputFile: File, iv: ByteArray, encryptedData: ByteArray, tag: ByteArray, aesKey: SecretKey) {
        val decrypted = AesUtils.decrypt(iv, encryptedData, tag, aesKey)
        outputFile.writeBytes(decrypted)
    }
}
