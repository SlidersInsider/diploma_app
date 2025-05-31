package com.mzhadan.app.diploma.crypto

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

object RsaUtils {
    fun encryptKey(aesKey: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(aesKey)
    }

    fun decryptKey(aesKey: ByteArray, privateKey: PrivateKey): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(aesKey)
    }
}
