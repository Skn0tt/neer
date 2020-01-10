package credentials

import UserID
import fromBase64
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

private val keyFactory = KeyFactory.getInstance("RSA")

private fun ByteArray.toJavaPublicKey(): java.security.PublicKey {
    val x509 = X509EncodedKeySpec(this)
    return keyFactory.generatePublic(x509)
}

data class PublicKey(val key: java.security.PublicKey) {

    constructor(key: ByteArray): this(key.toJavaPublicKey())
    constructor(key: String): this(key.fromBase64())

    fun verify(value: ByteArray, signature: ByteArray): Boolean {
        val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encrypted = cipher.doFinal(value)!!
        return encrypted.contentEquals(signature)
    }

    fun verify(value: String, signature: String) = verify(value.toByteArray(), signature.toByteArray())

}



interface PublicKeyRepo {
    operator fun get(user: UserID): PublicKey?
    operator fun set(user: UserID, value: PublicKey)
    fun remove(user: UserID)
    operator fun contains(user: UserID): Boolean
}