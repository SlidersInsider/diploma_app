package com.mzhadan.app.diploma.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

object CryptoManager {
    private const val KEY_ALIAS = "rsa_cryption_key"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    private fun keyExists(): Boolean {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return keyStore.containsAlias(KEY_ALIAS)
    }

    fun generateRsaKeyPair() {
        val generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setKeySize(2048)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
            build()
        }
        generator.initialize(spec)
        generator.generateKeyPair()
    }

    fun getPublicKey(): RSAPublicKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val cert = keyStore.getCertificate(KEY_ALIAS)
        return cert.publicKey as RSAPublicKey
    }

    fun getPrivateKey(): RSAPrivateKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        return keyStore.getKey(KEY_ALIAS, null) as RSAPrivateKey
    }
}
