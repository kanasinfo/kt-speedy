package com.kanasinfo.platform.utils

import org.apache.commons.codec.binary.Base64
import sun.security.util.DerInputStream

import java.io.IOException
import java.lang.Exception
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPrivateCrtKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * @author gefangshuai
 * @createdAt 2019-03-11 12:15
 */
class RSAUtils {
    companion object {
        /**
         * 获取PublicKey对象
         * @param publicKeyBase64
         * @return
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeySpecException
         */
        @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        fun getPublicKey(publicKeyBase64: String): PublicKey {
            val pem = publicKeyBase64
                .replace("\\-*BEGIN.*KEY\\-*".toRegex(), "")
                .replace("\\-*END.*KEY\\-*".toRegex(), "")
            java.security.Security.addProvider(
                org.bouncycastle.jce.provider.BouncyCastleProvider()
            )
            val pubKeySpec = X509EncodedKeySpec(Base64.decodeBase64(pem))
            val keyFactory = KeyFactory.getInstance("RSA")

            val publicKey = keyFactory.generatePublic(pubKeySpec)
            println(publicKey)
            return publicKey
        }

        /**
         * 获取PrivateKey对象
         * @param privateKeyBase64
         * @return
         */
        fun getPrivateKey(privateKeyBase64: String): PrivateKey {
            val privKeyPEM = privateKeyBase64
                .replace("\\-*BEGIN.*KEY\\-*".toRegex(), "")
                .replace("\\-*END.*KEY\\-*".toRegex(), "")

            // Base64 decode the data
            val encoded = Base64.decodeBase64(privKeyPEM)
            try {
                val derReader = DerInputStream(encoded)
                val seq = derReader.getSequence(0)

                if (seq.size < 9) {
                    throw GeneralSecurityException("Could not read private key")
                }

                // skip version seq[0];
                val modulus = seq[1].bigInteger
                val publicExp = seq[2].bigInteger
                val privateExp = seq[3].bigInteger
                val primeP = seq[4].bigInteger
                val primeQ = seq[5].bigInteger
                val expP = seq[6].bigInteger
                val expQ = seq[7].bigInteger
                val crtCoeff = seq[8].bigInteger

                val keySpec = RSAPrivateCrtKeySpec(
                    modulus, publicExp, privateExp,
                    primeP, primeQ, expP, expQ, crtCoeff
                )

                val factory = KeyFactory.getInstance("RSA")
                return factory.generatePrivate(keySpec)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            }
            throw Exception("生成私钥失败")
        }
    }
}
