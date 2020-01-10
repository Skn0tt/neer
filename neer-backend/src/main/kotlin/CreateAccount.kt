import credentials.PrivateKey
import credentials.PublicKeyRepo
import credentials.PublicKeyRepoInMemory
import credentials.generateKeyPair

private val publicKeyRepo: PublicKeyRepo = PublicKeyRepoInMemory
private val pushNotificationRepo: pushnotification.TokenRepo = pushnotification.TokenRepoInMemory

class DuplicateUserException : Exception("Duplicate User")

fun createAccount(username: UserID, pnToken: String, language: Language): PrivateKey {
    if (username in publicKeyRepo) throw DuplicateUserException()

    val keyPair = generateKeyPair()
    publicKeyRepo[username] = keyPair.publicKey

    pushNotificationRepo[username] = Pair(pnToken, language)

    return keyPair.privateKey
}