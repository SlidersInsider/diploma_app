package com.mzhadan.app.diploma.crypto

import java.io.File
import javax.crypto.SecretKey

object FileCryptoUtils {
    fun encryptFile(file: File, aesKey: SecretKey): Pair<ByteArray, ByteArray> {
        val data = file.readBytes()
        return AesUtils.encrypt(data, aesKey)
    }

    fun decryptFile(outputFile: File, iv: ByteArray, encryptedData: ByteArray, aesKey: SecretKey) {
        val decrypted = AesUtils.decrypt(iv, encryptedData, aesKey)
        outputFile.writeBytes(decrypted)
    }
}
