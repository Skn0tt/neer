import credentials.PublicKeyRepo
import credentials.PublicKeyRepoInMemory
import usedtokens.UsedTokensRepo
import usedtokens.UsedTokensRepoInMemory

private val publicKeyRepo: PublicKeyRepo = PublicKeyRepoInMemory
private val usedTokensRepo: UsedTokensRepo = UsedTokensRepoInMemory

fun authenticateUser(token: String): UserID? {
    val (userID, randomValue, signature) = token.split(":", limit = 3)

    if (usedTokensRepo.contains(userID, randomValue)) return null

    val publicKey = publicKeyRepo[userID] ?: return null
    if (!publicKey.verify(randomValue, signature)) return null

    usedTokensRepo.add(userID, randomValue)

    return userID
}