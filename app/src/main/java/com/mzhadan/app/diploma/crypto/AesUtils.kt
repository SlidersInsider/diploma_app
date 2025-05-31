package com.mzhadan.app.diploma.crypto

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesUtils {
    fun generateAesKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME)
        keyGen.init(256)
        return keyGen.generateKey()
    }

    fun encrypt(content: ByteArray, aesKey: SecretKey): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val iv = ByteArray(12).also { SecureRandom().nextBytes(it) }
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, GCMParameterSpec(128, iv))
        val encrypted = cipher.doFinal(content)
        return Pair(iv, encrypted)
    }

    fun decrypt(iv: ByteArray, encryptedData: ByteArray, aesKey: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, aesKey, GCMParameterSpec(128, iv))
        return cipher.doFinal(encryptedData)
    }

    fun keyFromBytes(keyBytes: ByteArray): SecretKey {
        return SecretKeySpec(keyBytes, "AES")
    }
}
