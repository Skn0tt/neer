package credentials

import java.security.KeyPairGenerator
import java.security.SecureRandom

typealias PrivateKey = ByteArray
data class KeyPair(val publicKey: PublicKey, val privateKey: PrivateKey)

private val generator = KeyPairGenerator.getInstance("RSA").apply {
    this.initialize(2048, SecureRandom())
}

fun generateKeyPair(): KeyPair {
    val kp = generator.generateKeyPair()
    return KeyPair(
        PublicKey(kp.public.encoded),
        kp.private.encoded
    )
}