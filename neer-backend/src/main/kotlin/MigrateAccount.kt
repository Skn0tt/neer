import credentials.PrivateKey
import credentials.PublicKeyRepo
import credentials.PublicKeyRepoInMemory
import credentials.generateKeyPair
import pushnotification.NotificationData
import pushnotification.sendNotification
import java.lang.Exception

private val publicKeyRepo: PublicKeyRepo = PublicKeyRepoInMemory

private fun String.validate(): UserID? {
    val (prefix, userID, signature) = this.split(":", limit = 3)

    if (prefix != "mig") return null
    val publicKey = publicKeyRepo[userID] ?: return null

    return if (publicKey.verify(userID, signature)) userID else null
}

class MigrationCodeIllegalException : Exception("Migration Code Illegal")

/**
 * Runs Migration and returns the calling client its new private key
 */
fun migrateAccount(migrationCode: String): PrivateKey {
    val userID = migrationCode.validate() ?: throw MigrationCodeIllegalException()
    publicKeyRepo.remove(userID)
    val newKeyPair = generateKeyPair()
    publicKeyRepo[userID] = newKeyPair.publicKey

    sendNotification(userID) { language ->
        val payload = mapOf("action" to "account_was_migrated")
        when (language) {
            Language.ENGLISH -> NotificationData("Your account has been moved to a different device.", null, payload)
            Language.GERMAN -> NotificationData("Dein Account wurde auf ein anderes Gerät übertragen.", null, payload)
        }
    }

    return newKeyPair.privateKey
}